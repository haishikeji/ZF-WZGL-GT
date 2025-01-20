<template>
    <div>
        <z-title title="供应商供货汇总" icon="el-icon-data-analysis"></z-title>
     <content-card>
         <!--通用搜索-->
         <el-card>
             <div class="flex-n fac">
                 <div class="comm-label">供应商名称</div>
                 <el-select class="ele-select" clearable  size="mini" v-model="supplierId" placeholder="请选择">
                     <el-option
                             v-for="item in supplierList"
                             :key="item.id"
                             :label="item.supplierName"
                             :value="item.id">
                     </el-option>
                 </el-select>

                 <!--日期默认为当前日期往前一个月-->
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
         <el-card class="mt-10">
             <div slot="header" class="flex-n fac fjb">
                 <div class="comm-card-title-one">供应商供货汇总列表</div>
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
    import rowName from './data/supplyRowName';
    import Pagination from "../../../components/pagination/pagination";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    export default {
        components: {
            ContentCard,
            Pagination,
            ZTitle},
        name: "supply",
        data(){
            return{
                pageSize:20,
                currentPage: 1,
                total: 0,
                loading:false,
                height:250,
                tableData:[],
                dateRange:[],
                rowName:[],
                supplierId:'',
                supplierList:[]
            }
        },
        mounted(){
            this.rowName=rowName;
            this.$nextTick(()=>{
                this.height=document.body.offsetHeight - 350 ;
            })
            this.searchList();
            this.getSupplier();
        },
        methods:{
            getSupplier(){
                this.$axios.post('/supplier/findList',{
                     "projectId":this.$root.getStorage('projectId'),
                }).then(res=>{
                    this.supplierList=res.data.list;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            searchList(){
                this.currentPage=1;
                this.loading=true;
                this.getDataList();
            },
            getDataList(){
                this.$axios.post('/report/supplierSummaryList', {
                    "projectId": this.$root.getStorage('projectId'),
                    "supplierId": this.supplierId,
                    "beginTime": this.dateRange?this.dateRange[0]:'',
                    "endTime": this.dateRange?this.dateRange[1]:'',
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
                this.supplierId='';
                this.dateRange=[];
            }
        }
    }
</script>

<style scoped>

</style>