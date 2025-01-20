<template>
    <el-dialog class="order-manage" :visible.sync="visible" :before-close="resetForm" width="1200px" append-to-body>
        <div slot="title" class="comm-toast-title-one">订单选择</div>
        <el-row :gutter="10" style="margin-bottom: -20px">
            <el-col :span="24">
                <el-card shadow="never">
                    <div class="flex-n fac fjb">
                        <div class="flex-n fac">
                            <div class="comm-label ">系统编号</div>
                            <el-input v-model="orderNo" class="w-140" clearable size="mini"
                                placeholder="请输入系统编号"></el-input>
                        </div>
                        <div class="flex-n fac">
                            <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
                            <el-button size="mini" class="ml-10" @click="resetData">重置</el-button>
                        </div>
                    </div>
                    <el-table class="mt-20" :data="tableData" cell-class-name="cell_table" v-loading="loading" show-overflow-tooltip
                        highlight-current-row :height="height + 'px'" element-loading-text="列表刷新中" style="width: 100%">
                        <el-table-column v-for="(item, index) in rowName" :key="index" show-overflow-tooltip
                            :width="item.width" :prop="item.prop" :label="item.label" :fixed="item.fixed ? item.fixed : false">
                            <template slot-scope="scope">
                                <template v-if="item.prop == 'todo'">
                                    <el-button type="text" @click="selectOrder(scope.row)">选择</el-button>
                                </template>
                                <template v-else>
                                    {{ scope.row[item.prop] }}
                                </template>
                            </template>
                        </el-table-column>
                    </el-table>
                    <pagination :current-page="currentPage" :page-size="pageSize" :total="total"
                        @sizeChange="handleSizeChange" @currentChange="handleCurrentChange"></pagination>
                </el-card>
            </el-col>
        </el-row>
    </el-dialog>
</template>

<script>
import Pagination from "@/components/pagination/pagination";

export default {
    components: {
        Pagination
    },
    name: "ImportOrder",
    props: {
        visible: Boolean
    },
    data() {
        return {
            pageSize: 20,
            currentPage: 1,
            total: 0,
            loading: false,
            height: 250,
            tableData: [],
            rowName: [{
                label: '订单编号',
                prop: 'orderNo',
                width: '100',
                fixed: 'left'
            }, {
                label: '供应商编号',
                prop: 'supplierNo',
                width: '100',
                fixed: ''
            }, {
                label: '供应商名称',
                prop: 'supplierName',
                width: '200',
                fixed: ''
            }, {
            label: '专业',
            prop: 'majorName',
            width: '80',
            fixed: ''
        }, {
            label: '要求到货时间',
            prop: 'requireArrivalDate',
            width: '140',
            fixed: ''
        }, {
            label: '到货地点',
            prop: 'arrivalPlace',
            width: '200',
            fixed: ''
        },{
            label: '预计到货时间',
            prop: 'preArrivalDate',
            width: '140',
            fixed: ''
        },{
            label: '制单人',
            prop: 'createUserName',
            width: '120',
            fixed: ''
        },
            {
                label: '创建时间',
                prop: 'createTime',
                width: '180',
                fixed: ''
            },
            {
                label: '操作',
                prop: 'todo',
                width: '200',
                fixed: 'right'
            }],
            orderNo: '',
        }
    },
    watch: {
        visible(newval, oldval) {
            if (newval) {
                this.getDataList();
            }
        }
    },
    methods: {
        searchList() {
            this.loading = true;
            this.currentPage = 1;
            this.getDataList();
        },
        getDataList() {
            this.$axios.post('/orderGood/orderGoodList', {
                "projectId": this.$root.getStorage('projectId'),
                "orderNo": this.orderNo,
                "approveStatus": '3',
                "operateType": '1',
                pageNum: this.currentPage,
                pageSize: this.pageSize
            }).then(res => {
                this.tableData = res.data.list;
                this.total = res.data.total;
            }).catch(err => {
                this.$message.error(err.msg)
            })
            this.loading = false;
        },

        selectOrder(data) {
            this.$emit('selectOrder', JSON.parse(JSON.stringify(data)));
            this.resetForm();
        },
        resetForm() {
            this.$emit('update:visible', false);
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
            this.orderNo = '';
            this.searchList();
        }
    }
}
</script>

<style scoped></style>