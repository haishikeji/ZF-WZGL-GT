<template>
    <div>
        <z-title title="退库管理" icon="el-icon-receiving"></z-title>
       <content-card>
           <!--通用搜索-->
           <el-card>
               <div class="flex-n fac">
                   <div class="comm-label ">退库单号</div>
                   <el-input v-model="returnNo" class="w-140" clearable size="mini" placeholder="请输入/扫描单号"  @keyup.enter.native="payCode"></el-input>
                   <div class="comm-label ml-10">审核状态</div>
                   <el-select class="ele-select" clearable  size="mini" v-model="approveStatus" placeholder="请选择">
                       <el-option
                               v-for="item in approveStatusList"
                               :key="item.codeValue"
                               :label="item.codeName"
                               :value="item.codeValue">
                       </el-option>
                   </el-select>
                   <div class="comm-label ml-10">仓库</div>
                   <el-select class="ele-select" clearable  size="mini" v-model="storeId" placeholder="请选择">
                       <el-option
                               v-for="item in storeList"
                               :key="item.id"
                               :label="item.storeName"
                               :value="item.id">
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
           </el-card>
           <el-card class="box-card mt-10">
               <div slot="header" class="flex-n fac fjb">
                   <div class="comm-card-title-one">退库列表</div>
                   <div class="flex-n fac">
                       <el-button size="mini" type="primary" @click="manageStockRetrun('',0)">新增</el-button>
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
                               <el-tag size="mini" v-if="scope.row.approveStatus==0" type="info">待审核</el-tag>
                               <el-tag size="mini" v-if="scope.row.approveStatus==1" type="warning">审核中</el-tag>
                               <el-tag size="mini" v-if="scope.row.approveStatus==2" type="danger">审核驳回</el-tag>
                               <el-tag size="mini" v-if="scope.row.approveStatus==3" type="success">审核成功</el-tag>

                           </template>
                           <template  v-else-if="item.prop=='todo'">
                               <el-button type="text" @click="manageStockRetrun(scope.row.id,1)">查看</el-button>
                               <template v-if="scope.row.approveStatus==0||scope.row.approveStatus==2">

                               <el-button type="text" @click="manageStockRetrun(scope.row.id,0)">编辑</el-button>
                               <el-popconfirm
                                       title="该操作将删除该数据，确定删除当前退库吗"
                                       @confirm="manageStockRetrun(scope.row.id,2)"
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
        <!--编辑退库-->
        <stockreturn-manage :visible.sync="editStockRetrunVisible" :id="id" @refreshList="searchList"></stockreturn-manage>
        <!--查看退库-->
        <stockreturn-detail :visible.sync="lookStockRetrunVisible" :id="id"></stockreturn-detail>

    </div>
</template>

<script>
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import rowName from './data/stockreturnRowName';
    import Pagination from "../../../components/pagination/pagination";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    import StockreturnDetail from "./component/StockReturnDetail";
    import StockreturnManage from "./component/StockReturnManage";

    export default {
        components: {
            StockreturnManage,
            StockreturnDetail,
            ContentCard,
            Pagination,
            ZTitle},
        name: "stock-return",
        data(){
            return{
                pageSize:20,
                currentPage: 1,
                total: 0,
                loading:false,
                height:250,
                tableData:[],

                rowName:[],

                returnNo:'',
                storeId:'',
                approveStatus:'',
                dateRange:[],

                storeList:[],
                approveStatusList:[],

                lookStockRetrunVisible:false,
                editStockRetrunVisible:false,
                id:''
            }
        },
        mounted(){
            this.rowName=rowName;
            this.$nextTick(()=>{
                this.height=document.body.offsetHeight - 380 ;
            })
            this.getStoreList();
            this.getApproveStatus();
            this.searchList();
        },
        methods:{
            payCode(){
                this.searchList();
            },
            getApproveStatus(){
                this.approveStatusList = this.$root.getStorage('dic').filter((item) => item.codeKind == 'approveStatus');
                console.log(this.approveStatusList)
            },
            getStoreList(){
              this.$axios.post('/store/storeList',{
                  projectId:this.$root.getStorage('projectId')
              }).then(res=>{
                  this.storeList=res.data.list;
              }).catch(err=>{
                  this.$message.error(err.msg)
              })
            },
            searchList(){
                this.currentPage=1;
                this.loading = true;
                this.getDataList();
            },
            getDataList() {
                this.$axios.post('/stockReturn/stockReturnList', {
                    "projectId": this.$root.getStorage('projectId'),
                    "returnNo": this.returnNo,
                    "storeId": this.storeId,
                    "approveStatus": this.approveStatus,
                    "beginTime": this.dateRange?this.dateRange[0]:'',
                    "endTime": this.dateRange?this.dateRange[1]:'',
                    pageNum: this.currentPage,
                    pageSize: this.pageSize
                }).then(res => {
                    this.tableData = res.data.list;
                    for(var i in this.tableData){
                        this.$set(this.tableData[i],'approveStatus',this.tableData[i].approveInfo[0].approveStatus)
                        this.$set(this.tableData[i],'approverUserName',this.tableData[i].approveInfo[0].approverUserName)
                        this.$set(this.tableData[i],'approveTime',this.tableData[i].approveInfo[0].approveTime)
                    }
                    this.total = res.data.total;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
                this.loading = false;
            },
            //增删改查
            manageStockRetrun(id,state){
                this.id=id;
                if(state==0){
                    this.editStockRetrunVisible=true;
                }
                if(state==1){
                    this.lookStockRetrunVisible=true;
                }
                if(state==2){
                    this.$axios.post('/stockReturn/logicDeleteByIds',id).then(res=>{
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
                this.returnNo = '';
                this.storeId = '';
                this.approveStatus = '';
                this.dateRange=[];
                this.searchList();
            }
        }
    }
</script>

<style scoped>

</style>