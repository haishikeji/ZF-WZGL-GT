<template>
    <div>
        <z-title title="项目管理" icon="el-icon-menu"></z-title>
   <content-card>
       <!--通用搜索-->
       <el-card>
           <div class="flex-n fac">
               <div class="comm-label ">项目名称</div>
               <el-input v-model="projectName" class="w-220" clearable size="mini" placeholder="请输入项目名称"></el-input>
               <div class="comm-label ml-10">项目编号</div>
               <el-input v-model="projectNo" class="w-220" clearable size="mini" placeholder="请输入项目编号"></el-input>

               <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
           </div>
       </el-card>
       <el-card class="mt-10">
           <div slot="header" class="flex-n fac fjb">
               <div class="comm-card-title-one">项目列表</div>
               <el-button size="mini" type="primary" class="ml-10" @click="manageProject('',0)">新增</el-button>
           </div>
           <el-table
                   :data="tableData"
                   cell-class-name="cell_table"
                   v-loading="loading"
                   highlight-current-row
                   :height="height+'px'"
                   element-loading-text="列表刷新中"
                   style="width: 100%">
               <el-table-column
                       v-for="(item,index) in rowName" :key="index"
                       show-overflow-tooltip
                       :fixed="item.fixed?item.fixed:false"
                       :width="item.width"
                       :prop="item.prop"
                       :label="item.label">
                   <template slot-scope="scope">
                       <template  v-if="item.prop=='todo'">
                           <el-button type="text" @click="manageProject(scope.row.id,1)">查看</el-button>
                           <el-button type="text" @click="manageProject(scope.row.id,0)">编辑</el-button>
                           <el-popconfirm
                                   title="确定删除当前项目吗？"
                                   @confirm="manageProject(scope.row.id,2)"
                           >
                               <el-button class="ml-10" slot="reference" type="text">删除</el-button>
                           </el-popconfirm>
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
        <!--编辑项目-->
        <project-manage :visible.sync="editProjectVisible" :id="id" @refreshList="searchList"></project-manage>
        <!--查看项目-->
        <project-detail :visible.sync="lookProjectVisible" :id="id"></project-detail>
    </div>
</template>

<script>
    import rowName from './data/projectRowName';
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import Pagination from "../../../components/pagination/pagination";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    import ProjectManage from "./component/ProjectManage";
    import ProjectDetail from "./component/ProjectDetail";

    export default {
        components: {
            ProjectDetail,
            ProjectManage,
            ContentCard,
            Pagination,
            ZTitle},
        name: "project",
        data(){
            return{
                pageSize:20,
                currentPage: 1,
                total: 0,
                loading:false,
                height:250,
                tableData:[],

                rowName:[],
                projectName:'',//项目名称
                projectNo:'',//项目编号

                lookProjectVisible:false,
                editProjectVisible:false,
                id:''
            }
        },
        mounted(){
            this.rowName=rowName;
            this.$nextTick(()=>{
                this.height=document.body.offsetHeight - 390 ;
            })
            this.searchList();
        },
        methods:{
            searchList(){
                this.currentPage=1;
                this.loading = true;
                this.getDataList();
            },
            getDataList(){
                this.$axios.post('/project/findList', {
                    "projectName": this.projectName,
                    "projectNo": this.projectNo,
                    pageNum: this.currentPage,
                    pageSize: this.pageSize,
                }).then(res => {
                    this.tableData = res.data.list;
                    this.total = res.data.total;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
                this.loading = false;
            },
            //增删改查
            manageProject(id,state){
                this.id=id;
                if(state==0){
                    this.editProjectVisible=true;
                }
                if(state==1){
                    this.lookProjectVisible=true;
                }
                if(state==2){
                    this.$axios.post('/project/logicDeleteByIds',id).then(res=>{
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
        }
    }
</script>

<style scoped>

</style>