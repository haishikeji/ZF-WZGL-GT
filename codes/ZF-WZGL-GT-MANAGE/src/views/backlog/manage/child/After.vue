<template>
    <div>
        <el-table class="mt-20"
                  :data="tableData"
                  cell-class-name="cell_table"
                  v-loading="loading"
                  highlight-current-row
                  :height="height+'px'"
                  element-loading-text="列表刷新中"
                  style="width: 100%">
            <el-table-column
                    v-for="(item,index) in columnList" :key="index"
                    :fixed="item.fixed"
                    :prop="item.prop"
                    :width="item.width"
                    :label="item.label">
                <template slot-scope="scope">
                    <template v-if="item.prop=='approveStatus'">
                        <el-tag size="mini" v-if="scope.row.approveStatus==0" type="info">待审核</el-tag>
                        <el-tag size="mini" v-if="scope.row.approveStatus==1" type="warning">审核中</el-tag>
                        <el-tag size="mini" v-if="scope.row.approveStatus==2" type="danger">审核驳回</el-tag>
                        <el-tag size="mini" v-if="scope.row.approveStatus==3" type="success">审核成功</el-tag>
                    </template>
                    <template  v-else>
                        {{scope.row[item.prop]}}
                    </template>
                </template>

            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="80"
            >
                <template slot-scope="scope">
                <el-button type="text" @click="manageProcedure(scope.row.id,scope.row.busId,scope.row.taskNodeId)">查看</el-button>
                </template>

            </el-table-column>
        </el-table>
        <pagination :current-page="currentPage" :page-size="pageSize" :total="total" @sizeChange="handleSizeChange"
                    @currentChange="handleCurrentChange"></pagination>
        <!--查看，审核申请-->
        <procedure :visible.sync="procedureVisible"  :id="id" :bus-id="busId" :taskNodeId="taskNodeId" @refreshList="searchList"></procedure>
    </div>
</template>

<script>
    import Pagination from "../../../../components/pagination/pagination";
    import Procedure from "../component/Procedure";

    export default {
        components: {
            Procedure,
            Pagination},
        name: "after",
        data(){
            return{
                id:'',
                busId:'',
                taskNodeId:'',
                pageSize:20,
                currentPage: 1,
                total: 0,
                loading:false,
                height:250,
                tableData:[],
                columnList:[{
                    label:'流程标题',
                    prop:'taskNodeName',
                    width:'',
                    fixed:'left'
                },{
                    label:'申请人',
                    prop:'applyUserName',
                    width:'100',
                    fixed:''
                },{
                    label:'流程状态',
                    prop:'approveStatus',// 0 待审核 1 审核中 2 审核驳回 3 审核成功
                    width:'100',
                    fixed:''
                },{
                    label:'申请时间',
                    prop:'applyTime',
                    width:'160',
                    fixed:''
                }],
                procedureVisible:false,
                procedureState:0
            }
        },
        mounted(){
            setTimeout(() => {
                this.height = document.body.offsetHeight - 320;
            }, 0)
            this.searchList();
        },
        watch: {
            '$store.state.workflow.search'(newval, oldval) {
                if (newval) {
                    this.searchList();
                }
            }
        },
        methods:{
            searchList(){
                this.currentPage=1;
                this.loading=true;
                this.getDataList();
            },
            getDataList() {
                this.$store.state.workflow.search = false;
                this.$axios.post('/workflow/goByMyApprove', {
                    "beginTime": this.$store.state.workflow.dateRange[0] || '',
                    "endTime": this.$store.state.workflow.dateRange[1] || '',
                    "taskNodeName": this.$store.state.workflow.taskNodeName || '',
                      "projectId": this.$root.getStorage('projectId'),
                    pageNum: this.currentPage,
                    pageSize: this.pageSize,
                }).then(res => {
                    this.tableData = res.data.list;
                    this.total = res.data.total;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
                this.loading=false;
            },
            manageProcedure(id,busId,taskNodeId){
                this.id=id;
                this.busId=busId;
                this.taskNodeId=taskNodeId;
                this.procedureVisible=true;
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
            }
        }
    }
</script>

<style scoped>

</style>