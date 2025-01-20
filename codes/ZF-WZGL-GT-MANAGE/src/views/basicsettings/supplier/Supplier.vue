<template>
    <div>
        <z-title title="供应商管理" icon="el-icon-menu"></z-title>
        <content-card>
            <!--通用搜索-->
            <el-card>
                <div class="flex-n fac">
                    <div class="comm-label">供应商编号</div>
                    <el-input v-model="supplierNo" class="w-220" clearable size="mini"
                              placeholder="请输入供应商编号"></el-input>
                    <div class="comm-label ml-10">供应商名称</div>
                    <el-input v-model="supplierName" class="w-220" clearable size="mini"
                              placeholder="请输入供应商名称"></el-input>
                    <div class="comm-label ml-10">联系电话</div>
                    <el-input v-model="mobile" class="w-220" clearable size="mini" placeholder="请输入联系电话"></el-input>
                    <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
                    <el-button size="mini" class="ml-10" @click="resetData">重置</el-button>

                </div>
            </el-card>
            <el-card class="mt-10">
                <div slot="header" class="flex-n fac fjb">
                    <div class="comm-card-title-one">供应商列表</div>
                    <el-button size="mini" type="primary" class="ml-10" @click="manageSupplier('',0)">新增</el-button>
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
                            <template v-if="item.prop=='todo'">
                                <el-button type="text" @click="manageSupplier(scope.row.id,0)">编辑</el-button>
                                <el-popconfirm
                                        title="确定删除当前供应商吗？"
                                        @confirm="manageSupplier(scope.row.id,2)"
                                >
                                    <el-button class="ml-10" slot="reference" type="text">删除</el-button>
                                </el-popconfirm>
                            </template>
                            <template v-else>
                                {{scope.row[item.prop]}}
                            </template>
                        </template>
                    </el-table-column>
                </el-table>
                <pagination :current-page="currentPage" :page-size="pageSize" :total="total"
                            @sizeChange="handleSizeChange"
                            @currentChange="handleCurrentChange"></pagination>
            </el-card>
        </content-card>
        <!--编辑供应商-->
        <supplier-manage :visible.sync="editSupplierVisible" :id="id" @refreshList="searchList"></supplier-manage>
    </div>
</template>

<script>
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import rowName from './data/supplierRowName';
    import Pagination from "../../../components/pagination/pagination";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    import SupplierManage from "./component/SupplierManage";

    export default {
        components: {
            SupplierManage,
            ContentCard,
            Pagination,
            ZTitle
        },
        name: "supplier",
        data() {
            return {
                pageSize: 20,
                currentPage: 1,
                total: 0,
                loading: false,
                height: 250,
                tableData: [],

                rowName: [],

                supplierNo: '',
                supplierName: '',
                mobile: '',

                list: [],

                editSupplierVisible: false,
                id: ''
            }
        },
        mounted() {
            this.rowName = rowName;
            this.$nextTick(()=>{
                this.height=document.body.offsetHeight - 390 ;
            })
            this.searchList();
        },
        methods: {
            searchList() {
                this.currentPage = 1;
                this.loading = true;
                this.getDataList();
            },
            getDataList() {
                this.$axios.post('/supplier/findList', {
                    "projectId":this.$root.getStorage('projectId'),
                    "supplierNo": this.supplierNo,
                    "supplierName": this.supplierName,
                    "mobile": this.mobile,
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
            manageSupplier(id, state) {
                this.id = id;
                if (state == 0) {
                    this.editSupplierVisible = true;
                }
                if (state == 2) {
                    this.$axios.post('/supplier/logicDeleteByIds',id).then(res=>{
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
                this.supplierNo = '';
                this.supplierName = '';
                this.mobile = '';
            }
        }
    }
</script>

<style scoped>

</style>