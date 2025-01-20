<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="1400px"  
    >
        <div slot="title" class="comm-toast-title-one" style="text-align: center;">申请计划详情</div>

        <el-card shadow="never">
            <div class="info-area">
                <div class="text">编号</div>
                <div class="num">{{info.handNo}}</div>
                <div class="text">系统编号</div>
                <div class="num">{{info.planNo}}</div>
                <div class="text">制单人</div>
                <div class="num">{{info.createUserName}}</div>
                <div class="text">制单日期</div>
                <div class="num">{{info.createTime}}</div>
                <div class="text">专业</div>
                <div class="num">{{info.majorName}}</div>
                <div class="text">计划到货日期</div>
                <div class="num">{{info.planOverDate}}</div>
                <div class="text">送货位置</div>
                <div class="num">{{info.place}}</div>
                <div class="text">备注</div>
                <div class="num">{{info.remark}}</div>
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
                        v-for="(item,index) in rowName" :key="index"
                        show-overflow-tooltip
                        :width="item.width"
                        :prop="item.prop"
                        :label="item.label"
                        :fixed="item.fixed?item.fixed:false">
                        <template slot-scope="scope">
                        <template>
                            <dev style="white-space: pre-wrap;  word-break: break-word; font-size: 12px;">{{ scope.row[item.prop] }}</dev>
                        </template>
                        </template>
                </el-table-column> <el-table-column
                        v-for="(item,index) in rowName2" :key="index"
                        show-overflow-tooltip
                        :width="item.width"
                        :prop="item.prop"
                        :label="item.label"
                        :fixed="item.fixed?item.fixed:false">
                        <template slot-scope="scope">
              <template>
                <dev style="white-space: pre-wrap;  word-break: break-word; font-size: 12px;">{{ scope.row[item.prop] }}</dev>
              </template>
            </template>
                </el-table-column>
            </el-table>
        </el-card>
    </el-dialog>
</template>

<script>
import rowName from '../../order/data/orderRowName';

    export default {
        name: "plan-detail",
        props: {
            visible: Boolean,
            id:String
        },
        data() {
            return {
                loading: false,
                height: 250,
                tableData: [],
                stationIntervalList:[],
                stationIntervalIds:[],
                stationIntervalSelectedList:[],
                rowName2:[],
                info:{},
                rowName: [
                    {
                        label: '物资编码',
                        prop: 'goodCode',
                        width: '100',
                        fixed: 'left'
                    }, {
                        label: '物资名称',
                        prop: 'goodName',
                        width: '200',
                        fixed: 'left'
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
                    // this.getData();
                }
            }
        },
        methods: {
            getData(){
                this.$axios.get('/planGood/findById/'+this.id).then(res=>{
                    console.log(res.data);
                    this.info=res.data;
                    this.stationIntervalIds=res.data.stationIntervalIds;
                    this.selectStationIntervalId(res.data.stationIntervalIds)
                    this.tableData=res.data.goodDetailObj;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            resetForm() {
                this.$emit('update:visible', false);
                this.stationIntervalList=[];
                this.stationIntervalIds=[];
                this.stationIntervalSelectedList=[];
                this. rowName2=[]
            },
            selectStationIntervalId(e){
                this.selected = new Set(e);
                this.stationIntervalSelectedList = this.stationIntervalList.filter(obj =>  this.selected.has(obj.id));
                if(this.stationIntervalSelectedList.length>0){
                    this.stationIntervalSelectedList.forEach(data => {
                        this.rowName2.push({
                            label: data.stationIntervalName,
                            prop: data.id,
                            prop2: 'num',
                            width: '',
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