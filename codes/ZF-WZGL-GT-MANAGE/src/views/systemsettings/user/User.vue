<template>
    <div>
        <z-title title="用户管理" icon="el-icon-s-tools"></z-title>
        <content-card>
            <!--通用搜索-->
            <el-card>
                <div class="flex-n fac">
                    <div class="comm-label ">用户名称</div>
                    <el-input v-model="userName" class="w-220" clearable size="mini" placeholder="请输入用户名称"></el-input>

                    <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
                </div>
            </el-card>
            <el-card class="box-card mt-10">
                <div slot="header" class="flex-n fac fjb">
                    <div class="comm-card-title-one">用户列表</div>
                    <el-button size="mini" v-if="$root.getStorage('isManage')" type="primary" class="ml-10"  @click="manageUser('',0)">新增</el-button>
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
                            :fixed="item.fixed"
                            :width="item.width"
                            :label="item.label">
                        <template slot-scope="scope" >
                            <template v-if="item.prop=='todo'">
                                <el-button type="text" @click="manageUser(scope.row.id,1)">查看</el-button>
                                <el-button type="text" v-if="$root.getStorage('isManage')" @click="manageUser(scope.row.id,0)">编辑</el-button>
                                <el-popconfirm
                                        title="确定删除当前用户吗？"
                                        @confirm="manageUser(scope.row.id,2)"
                                >
                                    <el-button class="ml-10" v-if="$root.getStorage('isManage')" slot="reference" type="text">删除</el-button>
                                </el-popconfirm>
                            </template>
                            <template v-else>
                                {{scope.row[item.prop]}}
                            </template>
                        </template>

                    </el-table-column>
                </el-table>
                <pagination :current-page="currentPage" :page-size="pageSize" :total="total" @sizeChange="handleSizeChange"
                            @currentChange="handleCurrentChange"></pagination>
            </el-card>
        </content-card>
        <!--编辑用户-->
        <user-manage :visible.sync="editUserVisible" :id="id" @refreshList="searchList"></user-manage>
        <!--查看用户-->
        <user-detail :visible.sync="lookUserVisible" :id="id"></user-detail>
    </div>
</template>

<script>
    import rowName from './data/userRowName';
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import Pagination from "../../../components/pagination/pagination";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    import UserManage from "./component/UserManage";
    import UserDetail from "./component/UserDetail";
    export default {
        components: {
            UserDetail,
            UserManage,
            ContentCard,
            Pagination,
            ZTitle},
        name: "user",
        data(){
            return{
                pageSize:20,
                currentPage: 1,
                total: 0,
                loading:false,
                height:250,
                tableData:[],

                rowName:[],
                userName:'',

                lookUserVisible:false,
                editUserVisible:false,
                id:''
            }
        },
        mounted(){
            this.rowName=rowName;
            this.$nextTick(()=>{
                this.height=document.body.offsetHeight - 380 ;
            })
            this.searchList();
        },
        methods:{
            searchList(){
                this.currentPage=1;
                this.loading=true;
                this.getDataList();
            },
            getDataList(){
                this.$axios.post('/user/findList', {
                    "projectId": this.$root.getStorage('projectId'),
                    "userName": this.userName,
                    pageNum: this.currentPage,
                    pageSize: this.pageSize,
                }).then(res => {
                    this.tableData = res.data.list;
                    this.total = res.data.total;
                }).catch(err => {
                    this.$message.error(err.msg);
                })
                this.loading=false;
            },
            //增删改查
            manageUser(id,state){
                this.id=id;
                if(state==0){
                    this.editUserVisible=true;
                }
                if(state==1){
                    this.lookUserVisible=true;
                }
                if(state==2){
                    this.$axios.post('/user/logicDeleteByIds',id).then(res=>{
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