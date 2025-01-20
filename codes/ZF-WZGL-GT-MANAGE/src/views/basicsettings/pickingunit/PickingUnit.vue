<template>
    <div>
        <z-title title="领料单位管理" icon="el-icon-menu"></z-title>
      <content-card>
          <!--通用搜索-->
          <el-card>
              <div class="flex-n fac">
                  <div class="comm-label ">领料单位编码</div>
                  <el-input v-model="pickCompanyNo" class="w-220" clearable size="mini" placeholder="请输入领料单位编码"></el-input>

                  <div class="comm-label ml-10">领料单位名称</div>
                  <el-input v-model="pickCompanyName" class="w-220" clearable size="mini" placeholder="请输入领料单位名称"></el-input>

                  <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
                  <el-button size="mini" class="ml-10" @click="resetData">重置</el-button>

              </div>
          </el-card>
          <el-card class="mt-10">
              <div slot="header" class="flex-n fac fjb">
                  <div class="comm-card-title-one">领料单位列表</div>
                  <el-button size="mini" type="primary" class="ml-10" @click="managePickUnit('',0)">新增</el-button>
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
                          :fixed="item.fixed?item.fixed:false"
                          :width="item.width"
                          :prop="item.prop"
                          :label="item.label">
                      <template slot-scope="scope">
                          <!--状态启用未启用-->
                          <template  v-if="item.prop=='todo'">
                              <el-button type="text" @click="managePickUnit(scope.row.id,1)">查看</el-button>

                              <el-button type="text" @click="managePickUnit(scope.row.id,0)">编辑</el-button>
                              <el-popconfirm
                                      title="确定删除当前领料单位吗"
                                      @confirm="managePickUnit(scope.row.id,2)"
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
        <!--编辑领料单位-->
        <pick-unit-manage :visible.sync="editPickUnitVisible" :id="id" @refreshList="searchList"></pick-unit-manage>
        <!--查看领料单位-->
        <pick-unit-detail :visible.sync="lookPickUnitVisible" :id="id"></pick-unit-detail>
    </div>
</template>

<script>
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import rowName from './data/pickingunitRowName';
    import Pagination from "../../../components/pagination/pagination";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    import PickUnitManage from "./component/PickUnitManage";
    import PickUnitDetail from "./component/PickUnitDetail";

    export default {
        components: {
            PickUnitDetail,
            PickUnitManage,
            ContentCard,
            Pagination,
            ZTitle},
        name: "picking-unit",
        data(){
            return{
                pageSize:20,
                currentPage: 1,
                total: 0,
                loading:false,
                height:250,
                tableData:[],

                rowName:[],
                pickCompanyNo:'',
                pickCompanyName:'',
                list:[],

                lookPickUnitVisible:false,
                editPickUnitVisible:false,
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
                this.$axios.post('/pickCompany/findList', {
                    "projectId":this.$root.getStorage('projectId'),
                    "pickCompanyNo": this.pickCompanyNo,
                    "pickCompanyName": this.pickCompanyName,
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
            managePickUnit(id,state){
                this.id=id;
                if(state==0){
                    this.editPickUnitVisible=true;
                }
                if(state==1){
                    this.lookPickUnitVisible=true;
                }
                if(state==2){
                    this.$axios.post('/pickCompany/logicDeleteByIds',id).then(res=>{
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
            resetData(){
                this.pickCompanyNo='';
                this.pickCompanyName='';
            }
        }
    }
</script>

<style scoped>

</style>