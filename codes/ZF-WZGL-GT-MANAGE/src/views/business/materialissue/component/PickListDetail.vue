<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="1200px"
            append-to-body
    >
        <div slot="title" class="comm-toast-title-one">领料单详情</div>

        <el-card shadow="never">
            <div class="info-area">
                <div class="text">领料单号</div>
                <div class="num">948893982</div>
                <div class="text">申请人</div>
                <div class="num">王五</div>
                <div class="text">申请日期</div>
                <div class="num">2020-02-31</div>
                <div class="text">仓库</div>
                <div class="num">丽丽</div>
                <div class="text">站区间</div>
                <div class="num">五二一也</div>
                <div class="text">审核状态</div>
                <div class="num">呼呼呼</div>
            </div>

            <el-table
                    class="mt-20"
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
                </el-table-column>
            </el-table>
        </el-card>
    </el-dialog>
</template>

<script>
    export default {
        name: "pick-list-detail",
        props: {
            visible: Boolean,
            id:String
        },
        data() {
            return {
                loading: false,
                height: 250,
                tableData: [{vehicleNumberPlate2: 1}],

                value1: '',
                rowName: [
                    {
                        label: '物资编号',
                        prop: 'vehicleNumberPlate',
                        width: '100',
                        fixed: ''
                    }, {
                        label: '物资名称',
                        prop: 'vehicleNumberPlate2',
                        width: '',
                        fixed: ''
                    }, {
                        label: '规格型号',
                        prop: 'vehicleNumberPlate2',
                        width: '100',
                        fixed: ''
                    }, {
                        label: '计量单位',
                        prop: 'vehicleNumberPlate2',
                        width: '80',
                        fixed: ''
                    }, {
                        label: '数量',
                        prop: 'num',
                        width: '80',
                        fixed: ''
                    }]
            }
        },
        methods: {
            resetForm() {
                this.$emit('update:visible', false);
            }
        }
    }
</script>

<style scoped lang="scss">
    .info-area {
        display: grid;
        width: 100%;
        grid-row-gap: 10px;
        grid-template-columns: repeat(3, 10% 23.3%);
        font-size: 14px;
        .text {
            text-align: right;
            margin-right: 20px;
        }
        .num {
            text-align: left;
            padding:0 10px;
            background: #f5f7fa;
            color: #c6c6c6;
        }
        .enclosure{
            grid-column-start: 5;
            grid-column-end: 7;
            text-align: right;
            cursor: pointer;
            color: #66b1ff;
        }
    }
</style>