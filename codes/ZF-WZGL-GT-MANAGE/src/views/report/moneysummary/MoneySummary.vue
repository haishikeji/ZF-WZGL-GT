<template>
    <div>
        <z-title title="收支存汇总" icon="el-icon-data-analysis"></z-title>
      <content-card>
          <!--通用搜索-->
          <el-card>
              <div class="flex-n fac">


                  <div class="comm-label">物资编号</div>
                  <el-input v-model="goodCode" class="w-120" clearable size="mini" placeholder="请输入物资编号"></el-input>
                  <div class="comm-label ml-10">物资名称</div>
                  <el-input v-model="goodName" class="w-120" clearable size="mini" placeholder="请输入物资名称"></el-input>
                  <div class="comm-label ml-10">规格型号</div>
                  <el-input v-model="specsModelName" class="w-120" clearable size="mini" placeholder="请输入规格型号"></el-input>
                  <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
                  <el-button size="mini" class="ml-10" @click="resetData">重置</el-button>

              </div>
          </el-card>
          <el-card class="mt-10">
              <div slot="header" class="flex-n fac fjb">
                  <div class="comm-card-title-one">收支存汇总列表</div>
                  <!--<el-button size="mini" type="primary" class="ml-10">导出</el-button>-->
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
                  </el-table-column>
              </el-table>
              <pagination :current-page="currentPage" :page-size="pageSize" :total="total" @sizeChange="handleSizeChange"
                          @currentChange="handleCurrentChange"></pagination>
          </el-card>
      </content-card>

    </div>
</template>

<script>
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import rowName from './data/summaryRowName';
    import Pagination from "../../../components/pagination/pagination";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    export default {
        components: {
            ContentCard,
            Pagination,
            ZTitle},
        name: "money-summary",
        data(){
            return{
                pageSize:20,
                currentPage: 1,
                total: 0,
                loading:false,
                height:250,
                tableData:[],

                rowName:[],
                goodName:'',
                specsModelName:'',
                goodCode:'',
                list:[]
            }
        },
        mounted(){
            this.rowName=rowName;
            this.$nextTick(()=>{
                this.height=document.body.offsetHeight - 350 ;
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
                this.$axios.post('/report/checkInSummaryList', {
                    "projectId": this.$root.getStorage('projectId'),
                    "goodCode": this.goodCode,
                    "goodName": this.goodName,
                    "specsModelName": this.specsModelName,
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
            }
        }
    }
</script>

<style scoped>

</style>