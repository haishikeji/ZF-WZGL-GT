<template>
  <el-dialog  :visible.sync="visible" :before-close="resetForm" width="1000px">
    <div >
      <table id="dialogContent"    class="table-bordered">
        <tr>
          <th :colspan="rowName2.length" style="border: none;font-size: 16px;">领用申请单</th>
        </tr>
        
        <tr>
          <td :colspan="rowName2.length" style="border: none;text-align: left;"> 
          </td>
        </tr>
        <tr>
          <td :colspan="rowName2.length" style="border: none;text-align: left;"> 
            <div style="width: 100%; display: flex; align-items: center;">
              <div style="width: 50%;">{{ '申请编号：'+ dataInfo.seqNo }}</div>
              <div style="width: 50%;">{{ '专业：' + dataInfo.majorName}}</div>
            </div>
          </td>
        </tr>
        <tr>
          <td :colspan="rowName2.length" style="border: none;text-align: left;"> 
            <div style="width: 100%; display: flex; align-items: center;">
              <div style="width: 50%;">{{ '站区间：' + dataInfo.stationIntervalName}}</div>
              <div style="width: 50%;">{{ '申请日期：' + dataInfo.createTime}}</div>
            </div>
          </td>
        </tr>
        
        <tr>
          <td :colspan="rowName2.length" style="border: none;text-align: left;"> 
            <div style="width: 100%; display: flex; align-items: center;">
              <div style="width: 50%;">{{ '申请单位：' + dataInfo.pickCompanyName}}</div>
              <div style="width: 50%;">{{ '申领人：' + dataInfo.pickUserName}}</div>
            </div>
          </td>
        </tr>
        <tr>
          <td :colspan="rowName2.length" style="border: none;text-align: left;"> 
            <div style="width: 100%; display: flex; align-items: center;">
              <div style="width: 50%;">{{ '编制单位：' + dataInfo.projectName}}</div>
              <div style="width: 50%;"></div>
            </div>
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
              <div style="width: 50%;">{{ '技术员：' }}</div>
              <div style="width: 50%;">{{ '作业队负责人：' }}</div>
            </div>
          </td>
        </tr>
      </table>
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
          label: "规格",
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
          label: "申请数量",
          prop: "num",
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
            this.getData()
        },
  watch: {
    visible(newval, oldval) {
        console.log(newval)
        this.getData();
      if (newval) {
        this.getNodeList();
        this.getApproveMsg();
        this.getApproveList();
      }
    },
  },
  methods: {
    getData() {
      console.log(this.taskNodeId+"--"+this.busId)
      if(!this.busId){
        return;
      }
      if(!this.taskNodeId){
        return;
      }
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
          this.tableGoodsData = res.data.goodDetail;
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