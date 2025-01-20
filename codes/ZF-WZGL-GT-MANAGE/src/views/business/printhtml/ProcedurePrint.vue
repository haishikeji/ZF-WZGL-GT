<template>
  <el-dialog
    class="order-manage"
    :visible.sync="visible"
    :before-close="resetForm"
    width="900px"
  >
    <div id="dialogContent">
      <div slot="title" class="comm-toast-title-one">
        {{ info.taskNodeName || "" }}
      </div>
      <el-steps
        :active="info.node==null?nodeList.length+1:info.node"
        align-center
        class="mt-10"
        finish-status="success"
      >
        <el-step
          :title="'申请人：' + info.createUserName"
          description="新建申请"
        ></el-step>
        <el-step
          :title="item.postName"
          v-for="(item, index) in nodeList"
          :key="index"
        ></el-step>
      </el-steps>
      <el-card shadow="never" class="mt-20">
        <div class="info-area">
          <div class="text" style=" width: 50px ">标题</div>
          <div class="num" tyle=" width: 200px " >{{ info.taskNodeName || "" }}</div>
          <div class="text" style=" width: 60px ">审核状态</div>
          <!--0 待审核 1 审核中 2 审核驳回 3 审核成功-->
          <div class="num" style=" width: 200px ">
            {{ info.approveStatus == 0 ? "待审核" : "" }}
            {{ info.approveStatus == 1 ? "审核中" : "" }}
            {{ info.approveStatus == 2 ? "审核驳回" : "" }}
            {{ info.approveStatus == 3 ? "审核成功" : "" }}
          </div>
          <div></div>
          <div></div>
          <div class="text" style=" width: 50px ">申请人</div>
          <div class="num" style=" width: 200px ">{{ info.applyUserName || "" }}</div>
          <div class="text" style=" width: 60px ">申请时间</div>
          <div class="num" style=" width: 200px " >{{ info.applyTime || "" }}</div>
          <div></div>
        </div>

        <el-table
          class="mt-20"
          :data="tableData"
          v-loading="loading"
          element-loading-text="列表刷新中"
          style="width: 100%"
        >
          <el-table-column
            v-for="(item, index) in rowName"
            :key="index"
            :width="item.width"
            :prop="item.prop"
            :label="item.label"
            :fixed="item.fixed ? item.fixed : false"
          >
            <template slot-scope="scope">
              <template v-if="item.prop == 'approveStatus'">
                <el-tag
                  size="mini"
                  v-if="scope.row.approveStatus == 0"
                  type="info"
                  >待审核</el-tag
                >
                <el-tag
                  size="mini"
                  v-if="scope.row.approveStatus == 1"
                  type="warning"
                  >审核中</el-tag
                >
                <el-tag
                  size="mini"
                  v-if="scope.row.approveStatus == 2"
                  type="danger"
                  >审核驳回</el-tag
                >
                <el-tag
                  size="mini"
                  v-if="scope.row.approveStatus == 3"
                  type="success"
                  >审核成功</el-tag
                >
              </template>
              <template v-else>
                {{ scope.row[item.prop] }}
              </template>
            </template>
          </el-table-column>
        </el-table>
        <!-- 物资详情 -->
        <el-table
          :data="tableGoodsData"
          v-loading="loading"
          element-loading-text="列表刷新中"
          style="width: 100%"
        >
          <el-table-column
            v-for="(item, index) in goodsRowName"
            :key="index"
            show-overflow-tooltip
            :width="item.width"
            :prop="item.prop"
            :label="item.label"
            :fixed="item.fixed ? item.fixed : false"
          >
            <template slot-scope="scope">
              <template>
                {{ scope.row[item.prop] }}
              </template>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
    <div slot="footer" style="margin-top: -20px">
      <el-button
        size="medium"
        type="primary"
        class="ml-10"
        v-print="'#dialogContent'"
        >打印</el-button
      >
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
          label: "物资名称",
          prop: "goodName",
          width: "120",
          fixed: "",
        },
        {
          label: "规格型号",
          prop: "specsModelName",
          width: "120",
          fixed: "",
        },
        {
          label: "物资类别",
          prop: "typeName",
          width: "120",
          fixed: "",
        },
        {
          label: "物资专业",
          prop: "majorName",
          width: "120",
          fixed: "",
        },
        {
          label: "数量",
          prop: "num",
          width: "80",
          fixed: "",
        },
        {
          label: "计量单位",
          prop: "measureUnit",
          width: "",
          fixed: "",
        },
      ],
      nodeList: [],
      info: {
        taskName: "",
      },
    };
  },
  watch: {
    visible(newval, oldval) {
      if (newval) {
        this.getNodeList();
        this.getApproveMsg();
        this.getApproveList();
        this.getData();
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
          switch (this.taskNodeId) {
            case "8":
              this.tableGoodsData = res.data.checkDetail;
              break;
            case "7":
              this.tableGoodsData = res.data.sendDetail;
              break;

            default:
              this.tableGoodsData = res.data.goodDetail;
              break;
          }
        })
        .catch((err) => {
          this.$message.error(err.msg);
        });
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
  font-size: 14px;
  .text {
    text-align: right;
    margin-right: 20px;
  }
  .num {
    text-align: left;
    padding: 0 10px;
    background: #f5f7fa;
    color: #c6c6c6;
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