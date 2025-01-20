<template>
  <el-dialog class="order-manage" :visible.sync="visible" :before-close="resetForm" width="1600px">
    <div id="dialogContent">
      <div slot="title" class="comm-toast-title-one" style="text-align: center;">{{ dataInfo.date || "" }} {{
    dataInfo.projectName || "" }}项目{{ dataInfo.majorName || "" }}专业供货通知单</div>

      <el-card shadow="never" class="mt-20">
        <div class="info-area" style="display: flex; align-items: center; ">
          <div class="text" style=" width: 80px ">供货单位：</div>
          <div class="num" style=" width: 300px ">{{ dataInfo.supplierName || "" }}</div>
          <div class="text" style=" width: 80px ">联系人：</div>
          <div class="num" style=" width: 300px "></div>

          <div class="text" style=" width: 80px ">电话：</div>
          <div class="num" style=" width: 300px "></div>
          <div class="text" style=" width: 60px ">编号：</div>
          <!--0 待审核 1 审核中 2 审核驳回 3 审核成功-->
          <div class="num" style=" width: 300px ">
            {{ dataInfo.handNo }}
          </div>
        </div>


        <el-table :data="tableGoodsData" v-loading="loading" element-loading-text="列表刷新中"
          style="width: 100% ;margin-top: 20px;">
          <el-table-column style="font-size: 10px;" v-for="(item, index) in rowName2" :key="index" show-overflow-tooltip
            :width="item.width" :prop="item.prop" :label="item.label" :fixed="item.fixed ? item.fixed : false">
            <template slot-scope="scope">
              <template>
                <dev style="white-space: pre-wrap;  word-break: break-word; font-size: 12px;">{{ scope.row[item.prop] }}
                </dev>
              </template>
            </template>
          </el-table-column>
        </el-table>


        <div class="info-area" style="display: flex; padding-top: 10px; align-items: center; ">
          <!-- <div class="text" style=" width: 100px ">编制人：</div>
          <div class="num" style=" width: 250px " >{{dataInfo.createUserName}}</div>
          <div class="text" style=" width: 100px ">审批人：</div>
          <div class="num" style=" width: 250px "></div>
          <div class="text" style=" width: 100px ">复核人：</div>
          <div class="num" style=" width: 250px "></div>
          <div class="text" style=" width: 100px ">批准人：</div>
          <div class="num" style=" width: 250px "></div>
          <div class="text" style=" width: 80px ">时间：</div>
          <div class="num" style=" width: 250px ">
            {{dataInfo.date}}
          </div> -->
          <!-- <div class="text" style=" width: 50px ">申请人</div>
          <div class="num" style=" width: 200px ">{{ info.applyUserName || "" }}</div>
          <div class="text" style=" width: 60px ">申请时间</div>
          <div class="num" style=" width: 200px " >{{ info.applyTime || "" }}</div>
          <div></div> -->
        </div>
      </el-card>
    </div>
    <div slot="footer" style="margin-top: -20px">
      <el-button size="medium" type="primary" class="ml-10" v-print="'#dialogContent'">打印</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "procedure-print",
  props: {
    visible: Boolean,
    id: String,
    busId: String,
    type: {
      type: String | Number,
      default: 0, //0查看 1审核
    },
    taskNodeId: String,
  },
  data() {
    return {
      stationIntervalList: [],
      stationIntervalIds: [],
      stationIntervalSelectedList: [],
      rowName2: {},
      showBudgetUnit: false, //概算量
      showBudgetDrawing: false, //图纸量
      showBudgetFixed: false, //定测量
      showOrderGood: false, //订货
      showReturnGood: false, //退货
      showPickOrder: false, //领料
      showSendOrder: false, //发料
      showCheckInput: false, //点验入库
      showOrderAllot: false, //调拨入库//调拨出库
      showInOutput: false, //直入直出
      showStockReturn: false, //退库
      showOrderCheck: false, //盘点
      showPlanOrder: false, //申请计划
      loading: false,
      height: 250,
      tableData: [],
      tableGoodsData: [],

      rowName: [
        {
          label: "审核人",
          prop: "approverPostName",
          width: "160",
          fixed: "",
        },
        {
          label: "审核时间",
          prop: "createTime",
          width: "180",
          fixed: "",
        },
        {
          label: "审核结果",
          prop: "approveStatus",
          width: "120",
          fixed: "",
        },
        {
          label: "审核意见",
          prop: "approveContent",
          width: "",
          fixed: "",
        },
      ],
      goodsRowName: [
        {
          label: "序号",
          prop: "no",
          width: "50",
          fixed: "",
        },
        {
          label: "物资名称",
          prop: "goodName",
          width: "",
          fixed: "",
        },
        {
          label: "规格型号",
          prop: "specsModelName",
          width: "",
          fixed: "",
        },
        {
          label: "物资类别",
          prop: "typeName",
          width: "",
          fixed: "",
        },
        {
          label: "计量单位",
          prop: "measureUnit",
          width: "80",
          fixed: "",
        },
        {
          label: "合计",
          prop: "total",
          width: "",
          fixed: "",
        },
      ],
      nodeList: [],
      info: {
        taskName: "",
      },
      dataInfo: {},
    };
  },
  watch: {
    visible(newval, oldval) {
      if (newval) {
        this.getStationIntervalList();
        this.getNodeList();
        this.getApproveMsg();
        this.getApproveList();
      }
    },
  },
  methods: {
    getData() {
      this.$axios
        .get(
          "/workflow/findDetailById/" +
          this.busId +
          "/" +
          this.taskNodeId +
          "/" +
          this.$root.getStorage("projectId")
        )
        .then((res) => {
          console.log(res.data);
          this.tableGoodsData = res.data.goodDetailObj;
          this.dataInfo = res.data
          this.stationIntervalIds = res.data.stationIntervalIds;
          this.selectStationIntervalId(res.data.stationIntervalIds)
        })
        .catch((err) => {
          this.$message.error(err.msg);
        });
    },
    selectStationIntervalId(e) {
      this.selected = new Set(e);
      this.stationIntervalSelectedList = this.stationIntervalList.filter(obj => this.selected.has(obj.id));
      const newBaseRowName = [];
      this.rowName2 = newBaseRowName.concat(this.goodsRowName);
      if (this.stationIntervalSelectedList.length > 0) {
        this.stationIntervalSelectedList.forEach(data => {
          this.rowName2.push({
            label: data.stationIntervalName,
            prop: data.id,
            prop2: 'num',
            width: '',
            fixed: ''
          });
        });
      }
      this.rowName2.push({
        label: "到货时间/地点",
        prop: "place",
        prop2: 'num',
        width: '',
        fixed: ''
      });
    },
    getStationIntervalList() {
      this.$axios.post('/stationInterval/findList', {
        "belongProjectId": this.$root.getStorage('projectId'),
      }).then(res => {
        this.stationIntervalList = res.data.list;
        this.getData();
      }).catch(err => {
        this.$message.error(err.msg)
      })
    },
    //获取节点
    getNodeList() {
      this.$axios
        .post("/workflow/findNode", {
          taskNodeId: this.taskNodeId,
        })
        .then((res) => {
          this.nodeList = res.data.list;
          console.log(this.nodeList)
          this.nodeList.push({
            postName: "审核完成",
          });
        })
        .catch((err) => {
          this.$message.error(err.msg);
        });
    },
    getApproveMsg() {
      this.$axios
        .post("/workflow/approveMsg", {
          busId: this.busId,
        })
        .then((res) => {
          console.log(res);
          this.info = res.data;
        })
        .catch((err) => {
          this.$message.error(err.msg);
        });
    },
    getApproveList() {
      this.$axios
        .post("/flowIns/approveList", {
          busId: this.busId,
        })
        .then((res) => {
          this.tableData = res.data.list;
        })
        .catch((err) => {
          this.$message.error(err.msg);
        });
    },

    //打印
    printAlert() {
      window.print(dialogContent);
    },
    lookDetail(taskNodeId) {
      switch (taskNodeId) {
        case "1": //概算量
          this.showBudgetUnit = true;
          break;
        case "2": //图纸量
          this.showBudgetDrawing = true;
          break;
        case "3": //定测量
          this.showBudgetFixed = true;
          break;
        case "4": //订货
          this.showOrderGood = true;
          break;
        case "5": //退货
          this.showReturnGood = true;
          break;
        case "6": //领料
          this.showPickOrder = true;
          break;
        case "7": //发料
          this.showSendOrder = true;
          break;
        case "8": //点验入库
          this.showCheckInput = true;
          break;
        case "9": //调拨入库
          this.showOrderAllot = true;
          break;
        case "10": //调拨出库
          this.showOrderAllot = true;
          break;
        case "11": //直入直出
          this.showInOutput = true;
          break;
        case "12": //退库
          this.showStockReturn = true;
          break;
        case "13": //盘点
          this.showOrderCheck = true;
          break;
        case "17": //申请计划
          this.showPlanOrder = true;
          break;
      }
    },
    resetForm() {
      this.$emit("update:visible", false);
      this.$emit("refreshList");
    },
  },
};
</script>

<style scoped lang="scss">
.info-area {
  display: grid;
  width: 100%;
  grid-row-gap: 10px;
  grid-template-columns: repeat(3, 10% 23.3%);
  font-size: 15px;

  .text {
    text-align: right;
  }

  .num {
    text-align: left;
    padding: 0 5px;
    // background: #f5f7fa;
    // color: #c6c6c6;
  }

  .btn {
    color: #66b1ff;
    cursor: pointer;
    text-align: right;
  }

  .input-area {
    grid-column-start: 2;
    grid-column-end: 7;
  }
}
</style>