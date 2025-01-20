<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="1200px"
    >
        <div slot="title" class="comm-toast-title-one">盘点表</div>

        <el-card shadow="never">
            <div class="info-area">
                <div class="text">盘点单号</div>
                <div class="num">{{info.checkNo}}</div>
                <div class="text">审核状态</div>
                <div class="num">
                    {{info.approveStatus==0?'待审核':''}}
                    {{info.approveStatus==1?'审核中':''}}
                    {{info.approveStatus==2?'审核驳回':''}}
                    {{info.approveStatus==3?'审核成功':''}}

                </div>
                <div class="text">盘点日期</div>
                <div class="num">{{info.checkTime}}</div>
                <div class="text">盘点仓库</div>
                <div class="num">{{info.storeName}}</div>
                <div class="text">盘点人</div>
                <div class="num">{{info.createUserName}}</div>
                <div class="text">创建时间</div>
                <div class="num">{{info.createTime}}</div>
                <div class="text">盈亏数量</div>
                <div class="num">{{info.resultNum}}</div>
                <div class="text">系统物资数量</div>
                <div class="num">{{info.stockSum}}</div>
                <div class="text">盘点物资数量</div>
                <div class="num">{{info.checkSum}}</div>
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
        name: "inventory-sheet",
        props: {
            visible: Boolean,
            id:String
        },
        data() {
            return {
                loading: false,
                height: 250,
                tableData: [],
                info:{},
                rowName: [
                    {
                        label: '物资编号',
                        prop: 'goodCode',
                        width: '100',
                        fixed: ''
                    }, {
                        label: '物资名称',
                        prop: 'goodName',
                        width: '',
                        fixed: ''
                    }, {
                        label: '规格型号',
                        prop: 'specsModelName',
                        width: '100',
                        fixed: ''
                    }, {
                        label: '计量单位',
                        prop: 'measureUnit',
                        width: '80',
                        fixed: ''
                    }, {
                        label: '库位',
                        prop: 'gridName',
                        width: '80',
                        fixed: ''
                    }, {
                        label: '库存数量',
                        prop: 'stockNum',
                        width: '80',
                        fixed: ''
                    }, {
                        label: '盘点数量',
                        prop: 'num',
                        width: '80',
                        fixed: ''
                    }, {
                        label: '盈亏数量',
                        prop: 'resultNum',
                        width: '80',
                        fixed: ''
                    }]
            }
        },
        watch:{
            visible(newval,oldval){
                if(newval){
                    this.getData();
                }
            }
        },
        methods: {
            getData(){
                this.$axios.get('/check/checkStatusList/'+this.id).then(res=>{
                    console.log(res.data);
                    this.info=res.data;
                    this.$set(this.info,'approveStatus',res.data.approveInfo[0].approveStatus);
                    this.tableData=res.data.checkDetail;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
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
        .remark{
            grid-column-start: 2;
            grid-column-end: 7;
        }
        .reason{
            grid-column-start: 4;
            grid-column-end: 7;
        }
    }
</style>