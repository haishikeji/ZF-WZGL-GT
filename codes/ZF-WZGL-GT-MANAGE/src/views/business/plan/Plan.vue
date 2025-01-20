<template>
    <div>
        <z-title title="申请计划" icon="el-icon-receiving"></z-title>
     <content-card>
         <!--通用搜索-->
         <el-card>
             <div class="flex-n fac">
                 <div class="comm-label ">系统编号</div>
                 <el-input v-model="planNo" class="w-140" clearable size="mini" placeholder="请输系统编号" @keyup.enter.native="payCode" ></el-input>
                 <div class="comm-label ">编号</div>
                 <el-input v-model="handNo" class="w-140" clearable size="mini" placeholder="请输编号" @keyup.enter.native="payCode" ></el-input>
                 <div class="comm-label ml-10">审核状态</div>
                 <el-select class="ele-select" clearable  size="mini" v-model="approveStatus" placeholder="请选择">
                     <el-option
                             v-for="item in approveStatusList"
                             :key="item.codeValue"
                             :label="item.codeName"
                             :value="item.codeValue">
                     </el-option>
                 </el-select>

                 <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
                 <el-button size="mini" class="ml-10" @click="resetData">重置</el-button>
                 <!-- <el-button size="mini" class="ml-10" @click="handleDownload">导出</el-button> -->

             </div>
         </el-card>
         <el-card class="box-card mt-10">
             <div slot="header" class="flex-n fac fjb">
                 <div class="comm-card-title-one">计划列表</div>
                 <div class="flex-n fac">
                     <el-button size="mini" type="primary" @click="managePlan('')">新增</el-button>
                     <!--<el-button size="mini" type="primary" class="ml-10" @click="exportData(list)">导出</el-button>-->
                 </div>
             </div>
             <el-table
                     :data="tableData"
                     cell-class-name="cell_table"
                     v-loading="loading"
                     show-overflow-tooltip
                     highlight-current-row
                     :height="height+'px'"
                     element-loading-text="列表刷新中"
                     style="width: 100%">
                 <el-table-column
                         v-for="(item,index) in rowName" :key="index"
                         show-overflow-tooltip
                         :width="item.width"
                         :prop="item.prop"
                         :label="item.label"
                         :fixed="item.fixed?item.fixed:false">
                     <template slot-scope="scope">
                         <template v-if="item.prop=='approveStatus'">
                             <el-tag size="mini" v-if="scope.row.approveStatus==-1">已保存</el-tag>
                             <el-tag size="mini" v-if="scope.row.approveStatus==0" type="info">待审核</el-tag>
                             <el-tag size="mini" v-if="scope.row.approveStatus==1" type="warning">审核中</el-tag>
                             <el-tag size="mini" v-if="scope.row.approveStatus==2" type="danger">审核驳回</el-tag>
                             <el-tag size="mini" v-if="scope.row.approveStatus==3" type="success">审核成功</el-tag>

                         </template>
                         <!--状态启用未启用-->
                         <template   v-else-if="item.prop=='todo'">
                             <el-button type="text" @click="getPlanDetail(scope.row.id)">查看</el-button>
                             <el-button type="text" @click="print(scope.row.tfiId,scope.row.id,scope.row.taskNodeId)">打印</el-button>
                             <el-button type="text" @click="handleDownload(scope.row.id)">导出</el-button>

                             <template v-if="scope.row.approveStatus==-1||scope.row.approveStatus==2">
                                <el-button type="text" @click="managePlan(scope.row.id)">编辑</el-button>
                                <el-popconfirm
                                        title="该操作将删除该数据，确定删除吗"
                                        @confirm="delOrder(scope.row.id)"
                                >
                                    <el-button class="ml-10" slot="reference" type="text">删除</el-button>
                                </el-popconfirm>
                             </template>
                         </template>
                         <template  v-else>
                             {{scope.row[item.prop]}}
                         </template>
                     </template>
                 </el-table-column>
             </el-table>
             <pagination :current-page="currentPage" :page-size="pageSize" :total="total" @sizeChange="handleSizeChange"
                         @currentChange="handleCurrentChange"></pagination>
         </el-card>
     </content-card>
        <!--编辑订货单-->
        <plan-manage :visible.sync="editOrderVisible" :id="id" @refreshList="searchList"></plan-manage>
        <!--查看订货单-->
        <plan-detail  :visible.sync="lookOrderVisible" :id="id" ></plan-detail>
        <procedure-print :visible.sync="printVisible" :id="id" :bus-id="busId" :taskNodeId="taskNodeId" @refreshList="searchList" ></procedure-print>

    </div>
</template>

<script>
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import rowName from './data/PlanRowName';
    import Pagination from "../../../components/pagination/pagination";
    import PlanManage from "./component/PlanManage";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    import PlanDetail from "./component/PlanDetail";
    import ProcedurePrint from "./printhtml/ProcedurePrint";

    export default {
        components: {
            PlanDetail,
            ContentCard,
            PlanManage,
            Pagination,
            ZTitle,
            ProcedurePrint},
        name: "plan",
        data(){
            return{
                pageSize:20,
                currentPage: 1,
                total: 0,
                loading:false,
                height:250,
                tableData:[],

                rowName:[],
                planNo:'',
                handNo:'',
                approveStatus:'',
                approveStatusList:[],
                editOrderVisible:false,
                lookOrderVisible:false,
                printVisible:false,
                id:'',
                busId:'',
                taskNodeId:''
            }
        },
        mounted(){
            this.rowName=rowName;
            this.$nextTick(()=>{
                this.height=document.body.offsetHeight - 380 ;
            })
            this.getApproveStatus();
            this.searchList();
        },
        methods:{
            print(id,busId,taskNodeId) {
                this.id=id;
                this.busId=busId;
                this.taskNodeId="17";
                this.printVisible = true;
            },
            payCode(){
               this.searchList();
            },
            getApproveStatus(){
                this.approveStatusList = this.$root.getStorage('dic').filter((item) => item.codeKind == 'approveStatus');
            },
            searchList(){
                this.currentPage=1;
                this.loading = true;
                this.getDataList();
            },
            getDataList() {
                this.$axios.post('/planGood/findAll', {
                    "projectId": this.$root.getStorage('projectId'),
                    "planNo": this.planNo,
                    "handNo": this.handNo,
                    "approveStatus": this.approveStatus,
                    pageNum: this.currentPage,
                    pageSize: this.pageSize
                }).then(res => {
                    this.tableData = res.data.list;
                    for(var i in this.tableData){
                        if(this.tableData[i].approveStatus>-1){
                            this.$set(this.tableData[i],'approveStatus',this.tableData[i].approveInfo[0].approveStatus)
                        }
                    }
                    this.total = res.data.total;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
                this.loading = false;
            },

            //查看订货单详情
            getPlanDetail(id){
                this.lookOrderVisible=true;
                this.id=id;
            },
            managePlan(id){
                this.editOrderVisible=true;
                this.id=id;
            },
            delOrder(id){
                this.$axios.post('/planGood/logicDeleteByIds',id).then(res=>{
                    this.$message.success('删除成功');
                    this.getDataList();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.loading = true;
                this.getDataList();
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.loading = true;
                this.getDataList();
            },
            handleDownload(id){
                this.$axios.post('/excel/planGoodsList',{
                    "id": id,
                },{ responseType: 'arraybuffer' }).then(res=>{
                    const datas = res;
                    const url = window.URL.createObjectURL(new Blob([datas], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" }))
                    const link = document.createElement('a')
                    link.style.display = 'none'
                    link.href = url;
                    link.setAttribute('download', '申请计划');
                    document.body.appendChild(link)
                    link.click()
                    document.body.removeChild(link);
                }).catch(err=>{
                    this.$message.error(err.msg)
                })                
            },
            exportData(datalist) {
                const {export_json_to_excel} = require('@/vendor/Export2Excel');
                const tHeader = ['仓库名称',  '库存货物', '仓库类别','额定库存量', '最大库存量', '最小库存量'];
                const filterVal = ['warehouseName',  'goodsName','warehouseTypeName', 'specifiedAmt', 'maxAmt', 'minAmt'];
                const data = this.formatJson(filterVal, datalist);
                export_json_to_excel(tHeader, data, '仓库基础信息列表');
            },

            formatJson(filterVal, jsonData) {
                return jsonData.map(v => filterVal.map(j => v[j]))
            },
            resetData() {
                this.planNo = '';
                this.approveStatus = '';
            }
        }

    }
</script>

<style scoped>

</style>

