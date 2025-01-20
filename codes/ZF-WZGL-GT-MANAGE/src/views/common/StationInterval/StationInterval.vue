<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="1000px"
            append-to-body
    >
        <div slot="title" class="comm-toast-title-one">区间选择</div>
        <el-row :gutter="10" style="margin-bottom: -20px">
            <el-col :span="5">
                <material-category shadow="never" @getId="getId"></material-category>
            </el-col>
            <el-col :span="19">
                <el-card shadow="never">
                    <div class="flex-n fac fjb">
                        <div class="flex-n fac">
                            <div class="comm-label ">区间名称</div>
                            <el-input v-model="goodCode" class="w-140" clearable size="mini"
                                      placeholder="请输入物资编号"></el-input>
                        </div>
                        <div class="flex-n fac">
                            <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
                            <el-button size="mini" class="ml-10" @click="resetData">重置</el-button>
                        </div>
                    </div>
                    <el-table
                            class="mt-20"
                            ref="table"
                            :data="tableData"
                            cell-class-name="cell_table"
                            v-loading="loading"
                            show-overflow-tooltip
                            highlight-current-row
                            :height="height+'px'"
                            element-loading-text="列表刷新中"
                            @selection-change="handleSelectionChange"
                            @row-click="handleRowSelectionChange"
                            style="width: 100%">
                        <el-table-column
                                v-if="type!=2&&type!=3"
                                type="selection"
                                :selectable="selectable"
                                width="55">
                        </el-table-column>
                        <el-table-column width='35' v-if="type==2||type==3">
                            <template slot-scope="scope">
                                <el-radio v-model="radioSelected" :label="scope.row.goodId"
                                          >{{' '}}
                                </el-radio>
                            </template>
                        </el-table-column>
                        <el-table-column
                                v-for="(item,index) in rowName" :key="index"
                                show-overflow-tooltip
                                :width="item.width"
                                :prop="item.prop"
                                :label="item.label"
                                :fixed="item.fixed?item.fixed:false">
                        </el-table-column>
                    </el-table>
                    <pagination :current-page="currentPage" :page-size="pageSize" :total="total"
                                @sizeChange="handleSizeChange"
                                @currentChange="handleCurrentChange"></pagination>
                </el-card>
            </el-col>
        </el-row>
        <span slot="footer" class="dialog-footer">
    <el-button size="medium" @click="resetForm">关 闭</el-button>
    <el-button size="medium" type="primary" @click="selectMaterial">选 择</el-button>
  </span>
    </el-dialog>
</template>

<script>
    import MaterialCategory from "../MaterialCategory/MaterialCategory";
    import Pagination from "../../../components/pagination/pagination";

    export default {
        components: {
            Pagination,
            MaterialCategory
        },
        name: "material-list",
        props: {
            visible: Boolean,
            type: {
                type: String | Number,
                default: 0
            },
            data: Array | Object,
            canSelect:Boolean
        },
        data() {
            return {
                pageSize: 20,
                currentPage: 1,
                total: 0,
                loading: false,
                height: 250,
                tableData: [],

                goodCode: '',
                goodName: '',
                specsModelName: '',
                goodType: '',

                rowName: [],
                multipleSelection: [],
                // 单选绑定
                radioSelected: null,
                // 表格选中row数据
                radioSelection: {},

                newData:[],
                newDataInfo:{}
            }
        },
        watch: {
            visible(newval, oldval) {
                if (newval) {
                    this.getRowName();
                    console.log(this.newData)

                        if(this.type==2||this.type==3){
                            this.newDataInfo=JSON.parse(JSON.stringify(this.data));
                        }else{
                            this.newData=JSON.parse(JSON.stringify(this.data));
                        }

                    this.getDataList();
                }
            }
        },
        methods: {
            selectable(row,index){
                console.log("row==========")
                if(row.stockNum==0&&!this.canSelect){
                    return false;
                }else{
                    return true;
                }
            },
            searchList() {
                this.loading = true;
                this.currentPage = 1;
                this.getDataList();
            },
            getId(id) {
                console.log(id)
                this.goodType = id;
                this.searchList();
            },
            //单选反显
            setRadioSelected(){
              console.log(this.data)
                for(var i in this.tableData){
                  if(this.tableData[i].goodId==this.newDataInfo.goodId){
                      this.radioSelected=this.newDataInfo.goodId;
                      break;
                  }
                }
            },
            setMultiSelected(){
                console.log(this.newData,this.tableData)
                var multipleSelection=[];
                    for (var i in this.tableData) {
                        console.log(this.tableData[i].goodId,this.newData)
                        for (var j in this.newData) {
                            if (this.newData[j].goodId == this.tableData[i].goodId) {
                                console.log('存在',this.tableData[i])
                                multipleSelection.push(this.tableData[i])

                                break;
                            }
                        }
                    }
                this.$nextTick(()=>{
                    for(var i in multipleSelection)
                    this.$refs.table.toggleRowSelection(multipleSelection[i], true);

                })
            },
            getDataList() {
                this.$axios.post('/good/goodList', {
                    "projectId": this.$root.getStorage('projectId'),
                    "goodCode": this.goodCode,
                    "goodName": this.goodName,
                    "specsModelName": this.specsModelName,
                    goodType: this.goodType,
                    pageNum: this.currentPage,
                    pageSize: this.pageSize,
                    type:this.type
                }).then(res => {
                    this.tableData = res.data.list;
                    for(var i in this.tableData){
                        this.tableData[i].goodId=this.tableData[i].id;
                    }
                    if(this.type==2||this.type==3){
                        this.setRadioSelected();
                    }else
                    this.setMultiSelected();
                    this.total = res.data.total;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
                this.loading = false;
            },
            getRowName() {
                var rowName = [{
                    label: '物资编号',
                    prop: 'goodCode',
                    width: '150',
                    fixed: ''
                }, {
                    label: '物资名称',
                    prop: 'goodName',
                    width: '',
                    fixed: ''
                }, {
                    label: '规格型号',
                    prop: 'specsModelName',
                    width: '100',
                    fixed: ''
                }, {
                        label: '物资类型',
                        prop: 'typeName',
                        width: '100',
                        fixed: ''
                    },{
                    label: '计量单位',
                    prop: 'measureUnit',
                    width: '80',
                    fixed: ''
                }, {
                    label: '数量',
                    prop: 'stockNum',
                    width: '120',
                    fixed: ''
                }];
                this.rowName = rowName.concat(rowData);
            },
            selectMaterial() {

if(this.type==2||this.type==3){
    this.$emit('getMaterial', JSON.parse(JSON.stringify(this.newDataInfo)));
}else{
    this.$emit('getMaterial', JSON.parse(JSON.stringify(this.newData)));
}
                this.resetForm();
            },
            resetForm() {
                this.newData=[];
                this.newDataInfo={};
                this.multipleSelection=[];
                this.radioSelected='';
                this.$refs.table.clearSelection();
                this.$emit('update:visible', false);
            },
            // 点击行选中,绑定数据
            handleRowSelectionChange(val) {
                console.log(val)
                if(this.type==2||this.type==3){
                    console.log('单选',val)
                    this.radioSelected = val.goodId  //选中行的name
                    this.radioSelection = val  //选中的一行数据
                    this.newDataInfo=val;
                    console.log(this.data,this.newDataInfo)
                }

            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
                var newData=JSON.parse(JSON.stringify(this.newData));
                console.log(val,newData,this.newData)
                //筛选出未被选中的list;
                var unSelectList = [];
                for (var i in this.tableData) {
                    var k = -1;
                    for (var j in this.multipleSelection) {
                        if (this.multipleSelection[j].goodId == this.tableData[i].goodId) {
                            k = j;
                            break;
                        }
                    }
                    if(k==-1){
                        unSelectList.push(this.tableData[i])
                    }
                }
                console.log('未选中',unSelectList)
                for (var j in unSelectList) {
                    for (var i = 0; i < newData.length; i++) {
                        if (unSelectList[j].goodId == newData[i].goodId) {
                            newData.splice(i, 1);
                            break;
                        }
                    }
                }
                var newList = [];

                for (var i in this.multipleSelection) {
                    var k = -1;
                    for (var j in newData) {
                        if (newData[j].goodId == this.multipleSelection[i].goodId) {
                            k = j;
                        }
                    }
                    if (k == -1) {
                        newList.push(this.multipleSelection[i]);
                    }
                }
                console.log(newData)
                this.newData =  newData.concat(newList)
                console.log('已选择列表',this.newData)
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.loading = true;
                this.getDataList();
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.loading = true;
                this.getDataList();
                console.log(`当前页: ${val}`);
            },
            resetData() {
                this.goodCode = '';
                this.goodName = '';
                this.specsModelName = '';
                this.searchList();
            }
        }
    }
</script>

<style scoped>

</style>