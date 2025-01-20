<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            append-to-body
            width="1200px"
    >
        <div slot="title" class="comm-toast-title-one">退货单详情</div>

        <el-card shadow="never">
            <div class="info-area">
                <div class="text">订单编号</div>
                <div class="num">{{info.returnNo}}</div>
                <div class="text">制单人</div>
                <div class="num">{{info.createUserName}}</div>
                <div class="text">制单日期</div>
                <div class="num">{{info.createTime}}</div>
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
        name: "look-returns-detail",
        props: {
            visible: Boolean,
            id:String,
            sceneId:String
        },
        data() {
            return {
                loading: false,
                height: 250,
                tableData: [],

                rowName: [
                    {
                        label: '物资编码',
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
                        label: '数量',
                        prop: 'num',
                        width: '80',
                        fixed: ''
                    },
                    {
                        label: '入库数',
                        prop: 'stockNum',
                        width: '80',
                        fixed: ''
                    },
                    {
                        label: '库位',
                        prop: 'gridName',
                        width: '100',
                        fixed: ''
                    }],
                info:{}
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
                this.$axios.get('/workflow/findDetailById/'+this.id+'/'+this.sceneId+'/'+this.$root.getStorage('projectId')).then(res=>{
                    console.log(res.data);
                    this.info=res.data;
                    this.tableData=res.data.goodDetail;
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
    }
</style>