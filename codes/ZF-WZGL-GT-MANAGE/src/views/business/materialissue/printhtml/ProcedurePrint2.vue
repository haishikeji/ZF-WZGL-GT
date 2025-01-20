<template>
  <el-dialog  :visible.sync="visible" :before-close="resetForm" width="1200px">
    <div >
      <table id="dialogContent"    class="table-bordered">
        <tr>
          <th :colspan="rowName2.length" style="font-size: 16px;">物 资 申 请 计 划 表</th>
        </tr>
        <tr>
          <th style="text-align: left; font-size: 14px;" :colspan="rowName2.length" >
            <div style="width: 100%; display: flex; align-items: center;">
              <div style="width: 50%;">{{ '编制单位：' +dataInfo.projectName }}</div>
              <div style="width: 50%;">{{ ' 编号：' + dataInfo.majorName + '--' + dataInfo.handNo}}</div>
            </div>
          </th>
        </tr>
        <tr>
          <td v-for="(item, index) in rowName2"  :key="index">{{ item.label }}</td>
        </tr>
        <tr v-for="item in tableGoodsData" :key="item.id">
          <td v-for="(item2, index) in rowName2"> {{ item[item2.prop] }}</td>
        </tr>
        <tr>
          <th style="text-align: left; font-size: 14px;" :colspan="rowName2.length" >
            <div style="width: 100%; display: flex; align-items: center;">
              <div style="width: 20%;">{{ '编制人：' + dataInfo.createUserName }}</div>
              <div style="width: 20%;">{{ '审批人：' }}</div>
              <div style="width: 20%;">{{ '复核人：' }}</div>
              <div style="width: 20%;">{{ '批准人：' }}</div>
              <div style="width: 20%;">{{ '时间：' + dataInfo.date }}</div>
            </div>
          </th>
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
      rowName2: [],
      loading: false,
      height: 250,
      tableData: [],
      tableGoodsData: [],
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
          width: "",
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
  watch: {
    visible(newval, oldval) {
      if (newval) {
        this.getStationIntervalList();
        this.getNodeList();
        this.getApproveMsg();
        this.getApproveList();
        // this.getData();
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
        label: "合计",
        prop: "total",
        width: "",
        fixed: "",
      });
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
        this.getData()
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

    getLength() {
      return this.rowName2.length / 2 + this.rowName2.length % 2
    },
    //打印
    printAlert() {
      window.print(dialogContent);
    },

    resetForm() {
      this.$emit("update:visible", false);
      this.$emit("refreshList");
      this.stationIntervalList = [];
      this.stationIntervalIds = [];
      this.stationIntervalSelectedList = [];
      this.rowName2 = []
    },
  },
};
</script>

<style scoped lang="scss">
.table-bordered {
  border-collapse: collapse;
  width: 100%; /* A4 宽度 */
  margin: auto; /* 水平居中 */

  td,
  th {
    padding: 12px 15px;
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