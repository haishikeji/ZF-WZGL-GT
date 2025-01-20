<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="1200px"
            append-to-body
    >
        <div slot="title" class="comm-toast-title-one">物资选择</div>
        <el-row :gutter="10" style="margin-bottom: -20px">
            <el-col :span="5">
                <material-category shadow="never" @getId="getId"></material-category>
            </el-col>
            <el-col :span="19">
                <el-card shadow="never">
                    <div class="flex-n fac fjb">
                        <div class="flex-n fac">
                            <div class="comm-label ">物资编号</div>
                            <el-input v-model="goodCode" class="w-140" clearable size="mini"
                                      placeholder="请输入物资编码"></el-input>
                            <div class="comm-label ml-10">物资名称</div>
                            <el-input v-model="goodName" class="w-140" clearable size="mini"
                                      placeholder="请输入物资名称"></el-input>
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
                            style="width: 100%">
                        <el-table-column
                                type="selection"
                                width="55">
                        </el-table-column>
                        <el-table-column
                                v-for="(item,index) in rowName" :key="index"
                                show-overflow-tooltip
                                :width="item.width"
                                :prop="item.prop"
                                :label="item.label"
                                :fixed="item.fixed?item.fixed:false">
                            <template slot-scope="scope">
                                <!--状态启用未启用-->
                                <template v-if="item.prop=='todo'">
                                    <el-button type="text" @click="lookDetail(scope.row.id)">查看</el-button>
                                </template>
                                <template v-else>
                                    {{scope.row[item.prop]}}
                                </template>
                            </template>
                        </el-table-column>
                    </el-table>
                    <pagination :current-page="currentPage" :page-size="pageSize" :total="total"
                                @sizeChange="handleSizeChange"
                                @currentChange="handleCurrentChange"></pagination>
                </el-card>
            </el-col>
        </el-row>
        <span slot="footer" class="dialog-footer" >
    <el-button size="medium" @click="resetForm">取 消</el-button>
    <el-button size="medium" type="primary" @click="selectMaterial">选 择</el-button>
  </span>
        <!--查看定测量详情-->
        <location-detail :visible.sync="lookDetailVisible" :id="id"></location-detail>
    </el-dialog>
</template>

<script>
    import MaterialCategory from "../../../common/MaterialCategory/MaterialCategory";
    import Pagination from "../../../../components/pagination/pagination";
    import LocationDetail from "./LocationDetail";

    export default {
        components: {
            LocationDetail,
            Pagination,
            MaterialCategory
        },
        name: "measure-material-info",
        props: {
            visible: Boolean,
            data: Array | Object,
        },
        data() {
            return {
                pageSize: 20,
                currentPage: 1,
                total: 0,
                loading: false,
                height: 250,
                tableData: [],


                rowName: [
                    {
                        label: '项目名称',
                        prop: 'vehicleNumberPlate',
                        width: '100',
                        fixed: ''
                    },
                    {
                        label: '物资编码',
                        prop: 'goodCode',
                        width: '100',
                        fixed: ''
                    }, {
                        label: '物资名称',
                        prop: 'goodName',
                        width: '',
                        fixed: ''
                    }, 
                    // {
                    //     label: '供应商名称',
                    //     prop: 'vehicleNumberPlate2',
                    //     width: '',
                    //     fixed: ''
                    // },
                    {
                        label: '物资类型',
                        prop: 'typeName',
                        width: '100',
                        fixed: ''
                    },
                     {
                        label: '规格型号',
                        prop: 'specsModelName',
                        width: '100',
                        fixed: ''
                    }, {
                        label: '计量单位',
                        prop: 'measureUnit',
                        width: '80',
                        fixed: ''
                    }, {
                        label: '合计',
                        prop: 'num',
                        width: '120',
                        fixed: ''
                    },
                    // {
                    //     label: '操作',
                    //     prop: 'todo',
                    //     width: '70',
                    //     fixed: ''
                    // }
                    ],
                multipleSelection:[],
                lookDetailVisible:false,
                id:'',

                goodCode:'',
                goodName: '',
                specsModelName:'',
                goodType:'',

                newData:[],
            }
        },
        watch:{
            visible(newval,oldval){
                if(newval){
                    this.newData=JSON.parse(JSON.stringify(this.data));
                    this.getDataList();
                }
            }
        },
        methods: {
            searchList(){
                this.loading=true;
                this.currentPage=1;
                this.getDataList();
            },
            getId(id){
                this.goodType=id;
                this.searchList();
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
            getDataList(){
                this.$axios.post('/good/goodList', {
                    "projectId": this.$root.getStorage('projectId'),
                    "goodCode": this.goodCode,
                    "goodName": this.goodName,
                    "specsModelName": this.specsModelName,
                    "filter":1,
                    goodType:this.goodType,
                    pageNum: this.currentPage,
                    pageSize: this.pageSize
                }).then(res => {
                    this.tableData = res.data.list;
                    for(var i in this.tableData){
                        this.tableData[i].goodId=this.tableData[i].id;
                    }
                    this.setMultiSelected();
                    this.total = res.data.total;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
                this.loading = false;
            },
            lookDetail(id){
                this.id=id;
                this.lookDetailVisible=true;
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
            selectMaterial(){
                this.$emit('getMaterial',JSON.parse(JSON.stringify(this.newData)));
                this.resetForm();
            },
            resetForm() {
                this.newData=[];
                this.multipleSelection=[];
                this.$refs.table.clearSelection();
                this.$emit('update:visible', false);
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
            resetData(){
                this.goodCode='';
                this.goodName='';
                this.specsModelName='';
                this.searchList();
            }
        }
    }
</script>

<style scoped>

</style>