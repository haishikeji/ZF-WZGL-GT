<template>
    <div>
        <z-title title="概算量管理" icon="el-icon-finished">
            <!--跳转过来时显示-->
            <el-button size="mini" type="primary" @click="getDataList">刷新</el-button>
            <el-button size="mini" class="ml-10"  @click="$router.go(-1)">返回</el-button>

        </z-title>
       <content-card>
           <!--通用搜索-->
           <el-card>
               <div class="flex-n fac">
                   <div class="comm-label ">物资编号</div>
                   <el-input v-model="goodCode" class="w-140" clearable size="mini" placeholder="请输入物资编号"></el-input>
                   <div class="comm-label ml-10">物资名称</div>
                   <el-input v-model="goodName" class="w-140" clearable size="mini" placeholder="请输入物资名称"></el-input>
                   <div class="comm-label ml-10">规格型号</div>
                   <el-input v-model="specsModel" class="w-140" clearable size="mini" placeholder="请输入规格型号"></el-input>

                   <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
                   <el-button size="mini" class="ml-10" @click="resetData">重置</el-button>

               </div>
           </el-card>
           <el-card class="box-card mt-10">
               <div slot="header" class="flex-n fac fjb">
                   <div class="comm-card-title-one">概算量列表</div>
                   <div class="flex-n fac">
                       <div class="comm-label">
                           {{approveStatus==''?'待提交':''}}
                           {{approveStatus=='0'?'待审核':''}}
                           {{approveStatus=='1'?'审核中':''}}
                           {{approveStatus=='2'?'审核驳回':''}}
                           {{approveStatus=='3'?'审核成功':''}}
                       </div>
                       <div class="comm-label ml-10">总计：{{total}}</div>
                       <template v-if="approveStatus==''">
                       <!--待审核状态四个不能弄-->
                       <el-button size="mini" type="primary" class="ml-20" @click="manageBudget('',0)">新增</el-button>
                       <!--提交二次确认弹窗-->
                       <el-button size="mini" type="primary" class="ml-10" @click="showSubmit=true">提交</el-button>
                       </template>
                       <!--<el-button size="mini" type="primary" class="ml-10" @click="importExcel">导入</el-button>-->
                       <!--<el-button size="mini" type="primary" class="ml-10">打印</el-button>-->

                   </div>
               </div>
               <el-table
                       ref="table"
                       :data="tableData"
                       cell-class-name="cell_table"
                       v-loading="loading"
                       show-overflow-tooltip
                       highlight-current-row
                       :height="height+'px'"
                       element-loading-text="列表刷新中"
                       style="width: 100%">
                   <el-table-column
                       min-width="100"
                           prop="projectName"
                           fixed="left"
                           label="项目名称">
                   </el-table-column>
                   <el-table-column
                           width="100"
                           prop="goodCode"
                           label="物资编号">
                   </el-table-column>
                   <el-table-column
                           width="100"
                           prop="goodName"
                           label="物资名称">
                   </el-table-column>
                   <el-table-column
                           width="80"
                           prop="specsModelName"
                           label="规格型号">
                   </el-table-column>
                   <el-table-column
                           width="80"
                           prop="unit"
                           label="单位">
                   </el-table-column>
                   <el-table-column
                           min-width="100"
                           v-for="(item,index) in budgetUnitList" :key="index"
                           :prop="item.unitName"
                           :label="item.unitName">
                   </el-table-column>
                   <el-table-column
                           prop="total"
                           width="120"
                           label="合计">
                   </el-table-column>
                   <el-table-column
                           width="200"
                           prop="remarks"
                           label="备注">
                   </el-table-column>
                   <el-table-column
                           width="140"
                           label="操作"

                           fixed="right">
                       <template  slot-scope="scope">
                           <el-button type="text" @click="manageBudget(scope.row.id,1)">查看</el-button>
                           <el-button type="text"  v-if="approveStatus==''" @click="manageBudget(scope.row.id,0)">编辑</el-button>
                           <el-popconfirm
                                   title="该操作将删除该订单所有数据，确定删除当前订单吗？"
                                   @confirm="manageBudget(scope.row.id,2)"
                           >
                               <el-button class="ml-10" slot="reference" type="text"  v-if="approveStatus==''">删除</el-button>
                           </el-popconfirm>
                       </template>
                   </el-table-column>
               </el-table>
               <!--<pagination :current-page="currentPage" :page-size="pageSize" :total="total" @sizeChange="handleSizeChange"-->
                           <!--@currentChange="handleCurrentChange"></pagination>-->
           </el-card>
           <!--编辑概算量-->
           <budget-manage :visible.sync="editBudgetVisible" :id="id" @refreshList="searchList"></budget-manage>
           <!--查看概算量-->
           <budget-detail  :visible.sync="lookBudgetVisible" :id="id"></budget-detail>
           <confirm-toast :visible.sync="showSubmit" title="该操作将提交该订单所有数据进行审核流程,确定要提交吗？" @submit="submitBudgetList"></confirm-toast>
       </content-card>
    </div>
</template>

<script>
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import rowName from './data/budgetRowName';
    import Pagination from "../../../components/pagination/pagination";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    import BudgetDetail from "./component/BudgetDetail";
    import BudgetManage from "./component/BudgetManage";
    import ConfirmToast from "../../../components/ConfirmToast/ConfirmToast";
    export default {
        components: {
            ConfirmToast,
            BudgetManage,
            BudgetDetail,
            ContentCard,
            Pagination,
            ZTitle},
        name: "budget",
        data(){
            return{
                approveStatus:'',
                goodCode:'',
                goodName:'',
                specsModel:'',
                pageSize:20,
                currentPage: 1,
                total: 0,
                loading:false,
                height:250,
                tableData:[],
                budgetUnitList:[],//动态概算单元列表

                rowName:[],
                list:[],

                lookBudgetVisible:false,
                editBudgetVisible:false,
                showSubmit:false,
                id:''
            }
        },
        mounted(){
            this.rowName=rowName;
            this.$nextTick(()=>{
                this.height=document.body.offsetHeight - 320 ;
            })
            this.searchList();

        },
        methods:{
            getState(){
              this.$axios.post('/workflow/threeEstimateStatus',{
                  "busId": this.$root.getStorage('projectId')+'1'
              }).then(res=>{
                  console.log(res.data)
                  this.approveStatus=res.data?.approveStatus||'';
              }).catch(err=>{
                  this.$message.error(err.msg)
              })
            },
            searchList(){
                // this.currentPage=1;
                this.loading=true;
                this.getBudgetUnit();
                this.getDataList();
                this.getState();
            },
            getDataList(){
                this.$axios.post('/threeEstimate/budgetEstimateList', {
                    "projectId": this.$root.getStorage('projectId'),
                    "goodCode": this.goodCode,
                    "goodName": this.goodName,
                    "specsModel": this.specsModel,
                    // pageNum: this.currentPage,
                    // pageSize: this.pageSize,
                }).then(res => {
                    this.tableData = res.data.list;
                    this.$nextTick(()=>{
                        this.$refs.table.doLayout();
                    })
                    this.total = res.data.total;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
                this.loading=false;
            },
            //获取动态概算单元
            getBudgetUnit(){
                this.$axios.get('/budgetUnit/findBudgetUnit/'+this.$root.getStorage('projectId')).then(res => {
                    this.budgetUnitList = res.data.list;
                  this.$nextTick(()=>{
                    this.$refs.table.doLayout();
                  })
                }).catch(err => {
                    this.$message.error(err.msg)
                })
            },
            //增删改查
            manageBudget(id,state){
                this.id=id;
                if(state==0){
                    this.editBudgetVisible=true;
                }
                if(state==1){
                    this.lookBudgetVisible=true;
                }
                if(state==2){
                    this.$axios.post('/threeEstimate/logicDeleteByIds',id).then(res=>{
                        this.$message.success('删除成功');
                        this.getBudgetUnit();
                        this.getDataList();
                    }).catch(err=>{
                        this.$message.error(err.msg)
                    })
                }
            },
            //导入
            importExcel(){
                // this.$axios.post('/budget/importExcel', {
                //     "file": '',
                // }).then(res => {
                //     this.$message.success('数据导入成功');
                // }).catch(err => {
                //     this.$message.error(err.msg)
                // })
            },
            //提交
            submitBudgetList(){
                this.$axios.post('/threeEstimate/commitBudgetUnit', {
                    "projectId":this.$root.getStorage('projectId'),
                }).then(res => {
                    this.$message.success('提交成功');
                    this.searchList();
                }).catch(err => {
                    this.$message.error(err.msg)
                })
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.loading = true;
                this.getBudgetUnit();
                this.getDataList();
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.loading = true;
                this.getBudgetUnit();
                this.getDataList();
                console.log(`当前页: ${val}`);
            },
            resetData(){

                    this.goodCode='';
                this.goodName='';
                this.specsModel='';
            }
        }
    }
</script>

<style scoped>

</style>