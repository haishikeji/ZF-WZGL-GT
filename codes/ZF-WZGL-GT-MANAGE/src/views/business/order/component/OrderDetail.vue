<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="1400px"
    >
        <div slot="title" class="comm-toast-title-one" style="text-align: center;">{{ info.date || "" }} {{ info.projectName || "" }}项目{{ info.majorName || "" }}专业供货通知单</div>

        <el-card shadow="never">
            <div class="info-area">
                <div class="text">编号</div>
                <div class="num">{{info.handNo}}</div>
                <div class="text">系统编号</div>
                <div class="num">{{info.orderNo}}</div>
                <div class="text">申请计划编号</div>
                <div class="num">{{info.planHandNo}}</div>
                <div class="text">制单人</div>
                <div class="num">{{info.createUserName}}</div>
                <div class="text">制单日期</div>
                <div class="num">{{info.createTime}}</div>
                <div class="text">供应商</div>
                <div class="num">{{info.supplierName}}</div>
                <div class="text">专业</div>
                <div class="num">{{info.majorName}}</div>
                <div class="text">要求到货日期</div>
                <div class="num">{{info.requireArrivalDate}}</div>
                <div class="text">要求到货地点</div>
                <div class="num">{{info.arrivalPlace}}</div>
            </div>

            <el-table
                    class="mt-20"
                    :data="tableData"
                    cell-class-name="cell_table"
                    v-loading="loading"
                    show-overflow-tooltip
                    highlight-current-row
                    element-loading-text="列表刷新中"
                    style="width: 100%">
                <el-table-column
                        v-for="(item,index) in rowName2" :key="index"
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
        name: "order-detail",
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

                stationIntervalList:[],
                stationIntervalIds:[],
                stationIntervalSelectedList:[],
                rowName2:{},
                rowName: [
                    {
                        label: '物资编码',
                        prop: 'goodCode',
                        width: '',
                        fixed: ''
                    }, {
                        label: '物资名称',
                        prop: 'goodName',
                        width: '',
                        fixed: ''
                    }, {
                        label: '规格型号',
                        prop: 'specsModelName',
                        width: '',
                        fixed: ''
                    },{
                        label: '物资类型',
                        prop: 'typeName',
                        width: '',
                        fixed: ''
                    },  {
                        label: '单位',
                        prop: 'measureUnit',
                        width: '80',
                        fixed: ''
                    },  {
                        label: '合计',
                        prop: 'total',
                        width: '',
                        fixed: ''
                    }]
            }
        },
        watch:{
            visible(newval,oldval){
                if(newval){
                    this.getStationIntervalList();
                    
                }
            }
        },
        methods: {
            getData(){
                this.$axios.get('/orderGood/findById/'+this.id).then(res=>{
                    console.log(res.data);
                    this.info=res.data;
                    this.tableData=res.data.goodDetailObj;
                    this.stationIntervalIds=res.data.stationIntervalIds;
                    this.selectStationIntervalId(res.data.stationIntervalIds)
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            resetForm() {
                this.$emit('update:visible', false);
            },
            selectStationIntervalId(e){
                this.selected = new Set(e);
                this.stationIntervalSelectedList = this.stationIntervalList.filter(obj =>  this.selected.has(obj.id));
                const newBaseRowName =  [];
                this.rowName2 = newBaseRowName.concat(this.rowName);
                if(this.stationIntervalSelectedList.length>0){
                    this.stationIntervalSelectedList.forEach(data => {
                        this.rowName2.push({
                            label: data.stationIntervalName,
                            prop: data.id,
                            prop2: 'num',
                            width: '150',
                            fixed: ''
                        });
                    });
                }
            },
            getStationIntervalList(){
                this.$axios.post('/stationInterval/findList',{
                    "belongProjectId": this.$root.getStorage('projectId'),
                }).then(res=>{
                    this.stationIntervalList=res.data.list;
                    this.getData();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
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