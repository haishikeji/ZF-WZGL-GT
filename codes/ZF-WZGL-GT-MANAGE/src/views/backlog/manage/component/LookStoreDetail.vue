<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            append-to-body
            width="1200px"
    >
        <div slot="title" class="comm-toast-title-one">入库单详情</div>

        <el-card shadow="never">
            <div class="info-area">
                <div class="text">入库单号</div>
                <div class="num">{{info.inputNo}}</div>
                <div class="text">审核原因</div>
                <div class="num">{{info.remarks}}</div>
                <div class="text">审核状态</div>
                <div class="num">
                    {{info.approveStatus==0?'待审核':''}}
                    {{info.approveStatus==1?'审核中':''}}
                    {{info.approveStatus==2?'审核驳回':''}}
                    {{info.approveStatus==3?'审核成功':''}}
                </div>
                <div class="text">供货商编号</div>
                <div class="num">{{info.supplierNo}}</div>
                <div class="text">供货商名称</div>
                <div class="num">{{info.supplierName}}</div>
                <!--<div class="text">联系人</div>-->
                <!--<div class="num">{{info.inputNo}}</div>-->
                <!--<div class="text">电话</div>-->
                <!--<div class="num">{{info.inputNo}}</div>-->
                <div class="text">制单人</div>
                <div class="num">{{info.createUserName}}</div>
                <div class="text">制单日期</div>
                <div class="num">{{info.createTime}}</div>
                <!--<div class="text">备注</div>-->
                <!--<div class="remark num">{{info.inputNo}}</div>-->
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
        name: "look-store-detail",
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
                        label: '物资编号',
                        prop: 'goodCode',
                        width: '',
                        fixed: ''
                    }, {
                        label: '物资名称',
                        prop: 'goodName',
                        width: '',
                        fixed: ''
                    },{
          label: "物资类别",
          prop: "typeName",
          width: "80",
          fixed: "",
        },
        {
          label: "所属专业",
          prop: "majorName",
          width: "",
          fixed: "",
        }, {
                        label: '规格型号',
                        prop: 'specsModelName',
                        width: '',
                        fixed: ''
                    }, {
                        label: '计量单位',
                        prop: 'measureUnit',
                        width: '',
                        fixed: ''
                    }, {
                        label: '入库数',
                        prop: 'num',
                        width: '',
                        fixed: ''
                    }, {
                        label: '库位',
                        prop: 'gridName',
                        width: '',
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
                    this.tableData=res.data.checkDetail;
                    this.$set(this.info,'approveStatus',res.data.approveInfo[0].approveStatus)
                    this.$set(this.info,'remarks',res.data.approveInfo[0].remarks)
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
    }
</style>