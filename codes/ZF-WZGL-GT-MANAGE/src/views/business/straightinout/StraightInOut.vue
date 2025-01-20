<template>
    <div>
        <z-title title="直入直出单管理" icon="el-icon-receiving"></z-title>
        <content-card>
            <el-tabs v-model="activeName" type="border-card"  @tab-click="handleClick">
                <el-tab-pane label="直入单" name="2"></el-tab-pane>
                <!-- <el-tab-pane label="直出单" name="1"></el-tab-pane> -->
                <div class="flex-n fac">
                    <div class="comm-label ">{{activeName==2?'直入':'直出'}}单号</div>
                    <el-input v-model="inOutputNo" class="w-140" clearable size="mini" placeholder="请输入/扫描单号" @keyup.enter.native="payCode"></el-input>
                    <div class="comm-label ml-10">仓库</div>
                    <el-select class="ele-select" clearable  size="mini" v-model="storeId" placeholder="请选择">
                        <el-option
                                v-for="item in storeList"
                                :key="item.id"
                                :label="item.storeName"
                                :value="item.id">
                        </el-option>
                    </el-select>
                    <div class="comm-label ml-10">单据状态</div>
                    <el-select class="ele-select" clearable  size="mini" v-model="approveStatus" placeholder="请选择">
                        <el-option
                                v-for="item in approveStatusList"
                                :key="item.codeValue"
                                :label="item.codeName"
                                :value="item.codeValue">
                        </el-option>
                    </el-select>
                    <div class="comm-label ml-10">日期</div>
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
                    <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
                    <el-button size="mini" class="ml-10" @click="resetData">重置</el-button>

                </div>
            </el-tabs>


            <el-card class="mt-10">
                <div slot="header" class="flex-n fac fjb">
                    <div class="comm-card-title-one">{{activeName==1?'直入':'直出'}}单列表</div>

                   <el-button size="mini" type="primary"  @click="manageStraight('',0)">新增</el-button>

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
                                <el-tag size="mini" v-if="scope.row.approveStatus==-1" >已保存</el-tag>
                                <el-tag size="mini" v-if="scope.row.approveStatus==0" type="info">待审核</el-tag>
                                <el-tag size="mini" v-if="scope.row.approveStatus==1" type="warning">审核中</el-tag>
                                <el-tag size="mini" v-if="scope.row.approveStatus==2" type="danger">审核驳回</el-tag>
                                <el-tag size="mini" v-if="scope.row.approveStatus==3" type="success">审核成功</el-tag>

                            </template>
                            <template  v-else-if="item.prop=='todo'">
                                <el-button type="text"  @click="manageStraight(scope.row.id,1)">查看</el-button>
                                <el-button type="text"
                                    @click="print(scope.row.tfiId, scope.row.id, scope.row.taskNodeId)">打印</el-button>
                                <template v-if="scope.row.approveStatus==-1||scope.row.approveStatus==2">

                                <el-button type="text"  @click="manageStraight(scope.row.id,0)">编辑</el-button>
                                <el-popconfirm
                                        title="该操作将删除该数据，确定删除吗"
                                        @confirm="manageStraight(scope.row.id,2)"
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

        <!--编辑直入直出-->
        <straight-manage :visible.sync="editStraightVisible" :id="id" :state="activeName" @refreshList="searchList"></straight-manage>
        <!--查看直入直出-->
        <straight-detail :visible.sync="lookStraightVisible" :id="id"></straight-detail>

        <procedure-print :visible.sync="printVisible" :id="id" :bus-id="busId" :taskNodeId="taskNodeId"
            @refreshList="searchList"></procedure-print>

    </div>
</template>

<script>
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import rowName from './data/inoutRowName';
    import Pagination from "../../../components/pagination/pagination";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    import StraightManage from "./component/StraightManage";
    import StraightDetail from "./component/StraightDetail";
    import ProcedurePrint from "./printhtml/ProcedurePrint";

    export default {
        components: {
            StraightDetail,
            StraightManage,
            ContentCard,
            Pagination,
            ZTitle,
            ProcedurePrint},
        name: "straight-in-out",
        data(){
            return{
                pageSize:20,
                currentPage: 1,
                total: 0,
                loading:false,
                height:250,
                tableData:[],

                rowName:[],

                storeList:[],
                inOutputNo:'',

                storeId:'',
                dateRange:[],
                approveStatus:'',

                approveStatusList:[],

                lookStraightVisible:false,
                editStraightVisible:false,
                id:'',
                busId: '',
                taskNodeId: '',
                printVisible: false,
                activeName: '2'
            }
        },
        mounted(){
            this.rowName=rowName;
            this.$nextTick(()=>{
                this.height=document.body.offsetHeight - 420 ;
            })
            this.getApproveStatus();
            this.searchList();
            this.getStoreList();
        },
        methods:{
            print(id, busId, taskNodeId) {
            this.id = id;
            this.busId = busId;
            this.taskNodeId = "11";
            this.printVisible = true;
        },
            payCode(){
                this.searchList();
            },
            handleClick(tab, event) {
                console.log(tab.name);
                this.getDataList();
            },
            getStoreList(){
                this.$axios.post('/store/storeList',{
                    "projectId": this.$root.getStorage('projectId'),
                }).then(res=>{
                    this.storeList=res.data.list;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            getApproveStatus(){
                this.approveStatusList = this.$root.getStorage('dic').filter((item) => item.codeKind == 'approveStatus');
                console.log(this.approveStatusList)
            },
            searchList(){
                this.currentPage=1;
                this.loading = true;
                this.getDataList();
            },
            getDataList() {
                this.$axios.post('/inOutput/inOutputList', {
                    "projectId": this.$root.getStorage('projectId'),
                    "inOutputType":this.activeName,
                    "inOutputNo": this.inOutputNo,
                    "storeId": this.storeId,
                    "beginTime": this.dateRange?this.dateRange[0]:'',
                    "endTime": this.dateRange?this.dateRange[0]:'',
                    "approveStatus": this.approveStatus,
                    pageNum: this.currentPage,
                    pageSize: this.pageSize
                }).then(res => {
                    this.tableData = res.data.list;
                    for(var i in this.tableData){
                        // this.$set(this.tableData[i],'approveStatus',this.tableData[i].approveInfo[0].approveStatus)
                        // this.$set(this.tableData[i],'approverUserName',this.tableData[i].approveInfo[0].approverUserName)
                        // this.$set(this.tableData[i],'approveTime',this.tableData[i].approveInfo[0].approveTime)

                        if(this.tableData[i].approveInfo.length>0){
                            this.$set(this.tableData[i],'approveStatus',this.tableData[i].approveInfo[0].approveStatus)
                            this.$set(this.tableData[i],'approverUserName',this.tableData[i].approveInfo[0].approverUserName)
                            this.$set(this.tableData[i],'approveTime',this.tableData[i].approveInfo[0].approveTime)
                        }else{
                            this.$set(this.tableData[i],'approveStatus',"-1")
                        }
                    }
                    this.total = res.data.total;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
                this.loading = false;
            },
            //增删改查
            manageStraight(id,state){
                this.id=id;
                if(state==0){
                    this.editStraightVisible=true;
                }
                if(state==1){
                    this.lookStraightVisible=true;
                }
                if(state==2){
                    this.$axios.post('/inOutput/deleteByIds',id).then(res=>{
                        this.$message.success('删除成功');
                        this.getDataList();
                    }).catch(err=>{
                        this.$message.error(err.msg)
                    })
                }
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
                this.inOutputNo = '';
                this.storeId='';
                this.approveStatus = '';
                this.dateRange=[];
                this.searchList();
            }
        }
    }
</script>

<style scoped>

</style>