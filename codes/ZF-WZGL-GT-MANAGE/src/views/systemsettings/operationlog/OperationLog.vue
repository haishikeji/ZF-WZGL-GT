<template>
    <div>
        <z-title title="操作日志" icon="el-icon-s-tools"></z-title>
       <content-card>
           <!--通用搜索-->
           <el-card>
               <div class="flex-n fac">
                   <div class="comm-label ">操作账号</div>
                   <el-select class="ele-select" clearable  size="mini" :value="value1" placeholder="请选择">
                       <el-option
                               v-for="item in list"
                               :key="item"
                               :label="item"
                               :value="item">
                       </el-option>
                   </el-select>
                   <div class="comm-label ml-10">操作日期</div>
                   <el-date-picker
                           v-model="value1"
                           size="mini"
                           value-format="yyyy-MM-dd"
                           type="daterange"
                           clearable
                           range-separator="至"
                           start-placeholder="开始日期"
                           end-placeholder="结束日期">
                   </el-date-picker>
                   <el-button size="mini" type="primary" class="ml-10">查询</el-button>
               </div>
           </el-card>
           <el-card class="box-card mt-10">
               <div slot="header">
                   <div class="comm-card-title-one">操作日志列表</div>
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
    import rowName from './data/logRowName'
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import Pagination from "../../../components/pagination/pagination";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    export default {
        components: {
            ContentCard,
            Pagination,
            ZTitle},
        name: "operation-log",
        data(){
            return{
                pageSize:20,
                currentPage: 1,
                total: 0,
                loading:false,
                height:250,
                tableData:[{}],

                rowName:[],
                value1:'',
                list:[]
            }
        },
        mounted(){
            this.rowName=rowName;
        },
        methods:{
            getDataList(){

            },
            delPost(id){

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