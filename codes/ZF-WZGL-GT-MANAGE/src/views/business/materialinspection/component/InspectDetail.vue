<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="1000px"
    >
        <div slot="title" class="comm-toast-title-one">检测记录详情</div>

        <el-card shadow="never">
            <div class="info-area">
                <div class="text">物资编号</div>
                <div class="num">{{info.goodCode}}</div>
                <div class="text">物资名称</div>
                <div class="num">{{info.goodName}}</div>
                <div class="text">规格型号</div>
                <div class="num">{{info.specsModelName}}</div>
                <div class="text">入库时间</div>
                <div class="num">{{info.inputTime}}</div>
                <div class="text">入库数量</div>
                <div class="num">{{info.inputNum}}</div>
                <div class="text">入库计量单位</div>
                <div class="num">{{info.measureUnit}}</div>
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
        name: "inspect-manage",
        props: {
            visible: Boolean,
            id:String
        },
        data() {
            return {
                loading: false,
                height: 250,
                tableData: [],

                info: {},
                rowName: [
                    {
                        label: '检测记录日期',
                        prop: 'checkTime',
                        width: '',
                        fixed: ''
                    }, {
                        label: '检测计量单位',
                        prop: 'testUnit',
                        width: '',
                        fixed: ''
                    }, {
                        label: '检测数量',
                        prop: 'checkNum',
                        width: '100',
                        fixed: ''
                    }, {
                        label: '检测人',
                        prop: 'createUserName',
                        width: '',
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
                this.$axios.get('/goodCheck/findById/'+this.id).then(res=>{
                    console.log(res.data);
                    this.info=res.data;
                    this.tableData=res.data.logDetail;
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
        grid-template-columns: repeat(3, 12% 21.3%);
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