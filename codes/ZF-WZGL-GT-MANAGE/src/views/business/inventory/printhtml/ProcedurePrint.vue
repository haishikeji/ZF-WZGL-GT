<template>
  <el-dialog  :visible.sync="visible" :before-close="resetForm" width="1000px">
    <el-card shadow="never" >
  <div class="flex-n fac">
          <div class="comm-label ">请选择盘点时间</div>
          <el-date-picker
                            v-model="dateRange"
                            size="mini"
                            value-format="yyyy-MM-dd"
                            type="daterange"
                            clearable
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期">
                    </el-date-picker>
          <el-button size="mini" type="primary" class="ml-10" @click="getData">查询</el-button>
          <el-button size="mini" type="primary" class="ml-10" v-print="'#dialogContent'">打印</el-button>

      </div>
      </el-card>
    <div >
      <table id="dialogContent"    class="table-bordered">
        <tr>
          <th :colspan="rowName2.length" style="border: none;font-size: 16px;">物资盘点表</th>
        </tr>
        
        <tr>
          <td :colspan="rowName2.length" style="border: none;text-align: left;"> 
            <div style="width: 100%; display: flex; align-items: center;">
              <div style="width: 50%;">类别：</div>
              <div style="width: 50%;"></div>
            </div>
          </td>
        </tr>
        <tr>
          <td :colspan="rowName2.length" style="border: none;text-align: left;"> 
          </td>
        </tr>
       
        <tr>
          <td :colspan="rowName2.length" style="border: none;text-align: left;"> 
          </td>
        </tr>
        
        <tr>
          <td  v-for="(item, index) in rowName2" :width="item.width"  :key="index">{{ item.label }}</td>
        </tr>
        <tr v-for="item in tableGoodsData" :key="item.id">
          <td v-for="(item2, index) in rowName2"> {{ item[item2.prop] }}</td>
        </tr>

        
        <tr>
          <td :colspan="rowName2.length" style="border: none;text-align: left;"> 
          </td>
        </tr>
        <tr>
          <td style="text-align: left; border: none;" :colspan="rowName2.length" >
            <div style="width: 100%; display: flex; align-items: center;">
              <div style="width: 50%;">{{ '业务员：' }}</div>
              <div style="width: 50%;">{{ '管理员：' }}</div>
            </div>
          </td>
        </tr>
        <tr>
          <td :colspan="rowName2.length" style="border: none;text-align: left;"> 
          </td>
        </tr>
        <tr>
          <td style="text-align: left; border: none;" :colspan="rowName2.length" >
            <div style="width: 100%; display: flex; align-items: left;">
              说明:备注栏应说明物资的来源、现状必要时须按序号另附点验说明。按月盘点，报公司物资设备运输部。
            </div>
          </td>
        </tr>

      </table>
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
      dateRange:[],
      loading: false,
      height: 250,
      tableData: [],
      tableGoodsData: [],
      rowName2: [
        {
          label: "序号",
          prop: "no",
          width: "",
          fixed: "",
        },
        {
          label: "材料名称",
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
          label: "单位",
          prop: "measureUnit",
          width: "",
          fixed: "",
        },
        {
          label: "入库量",
          prop: "inNum",
          width: "",
          fixed: "",
        },
        {
          label: "出库量",
          prop: "outNum",
          width: "",
          fixed: "",
        },
        {
          label: "库存量",
          prop: "stockNum",
          width: "",
          fixed: "",
        },
        {
          label: "专业",
          prop: "majorName",
          width: "",
          fixed: "",
        },
        {
          label: "说明",
          prop: "remark",
          width: "200",
          fixed: "",
        }

      ],
      nodeList: [],
      info: {
        taskName: "",
      },
      dataInfo: {},
    };
  }, 
  mounted(){
        this.getFirstAndLastDayOfMonth()
            this.getData()
        },
  watch: {
    visible(newval, oldval) {
        console.log(newval)
        this.getFirstAndLastDayOfMonth()
        this.getData();
      if (newval) {
        this.getNodeList();
        this.getApproveMsg();
        this.getApproveList();
      }
    },
  },
  methods: {
    getFirstAndLastDayOfMonth() {
      const today = new Date();
      const year = today.getFullYear();
      const month = today.getMonth(); // 从0开始的月份索引
      
      // 当前月份的第一天
      // 当前月份的最后一天
      this.dateRange[0] =  new Date(year, month, 1);
      this.dateRange[1] =  new Date(year, month + 1, 0);
    },
    getData() {
      console.log(this.taskNodeId+"--"+this.busId)
      if(!this.busId){
        return;
      }
      if(!this.taskNodeId){
        return;
      }
      this.$axios
        .post(
          "/check/findPrintDetail" ,{
          "id":this.busId,
          "projectId": this.$root.getStorage('projectId'),
          "beginTime": this.dateRange?this.dateRange[0]:'',
          "endTime": this.dateRange?this.dateRange[1]:'',
        }).then((res) => {
          console.log(res.data);
          this.tableGoodsData = res.data.checkDetail;
          var no = 1;
          for(var i in this.tableGoodsData){
           this.tableGoodsData[i].no = no
            no++;
          }
          this.dataInfo = res.data
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

    resetForm() {
      this.$emit("update:visible", false);
      this.$emit("refreshList");
      this.dataInfo={}
      this.tableGoodsData = []
    },
  },
};
</script>

<style scoped lang="scss">
.table-bordered {
  border-collapse: collapse;
  width: 95%; /* A4 宽度 */
  margin: auto; /* 水平居中 */

  td,
  th {
    padding: 5px 2px;
    border: 1px solid black;
    text-align: center;
    width: 20;
  }

}

@media print {
  
  /* 打印时应用的样式 */
  html, body {
    width: 210mm;
    margin: 0;
    padding: 0;
  }
  .a4-landscape {
    margin: 10mm; /* 页边距 */
    width: 210mm; /* 内容宽度 */
  }
}
</style>