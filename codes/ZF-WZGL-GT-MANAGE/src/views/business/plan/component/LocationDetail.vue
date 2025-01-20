<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="1200px"
            append-to-body
    >
        <div slot="title" class="comm-toast-title-one">定测量详情</div>

        <el-card shadow="never">
            <div class="info-area" >
                <div class="text">物资编号</div>
                <div class="num">{{info.goodCode}}</div>
                <div class="text">物资名称</div>
                <div class="num">{{info.goodName}}</div>
                <div class="text">单位</div>
                <div class="num">{{info.unit}}</div>
                <div class="text">规格型号</div>
                <div class="num">{{info.specsModelName}}</div>
            </div>
            <div class="info-area-2" >
                <div class="item" v-for="(item,index) in stationScheduleList" :key="index">
                    <div class="text">{{item.stationIntervalName}}</div>
                    <div class="num">{{item.stationScheduleValue}}</div>
                </div>
            </div>
            <div class="info-area" >
                <div class="text">合计</div>
                <div class="num">{{info.total}}</div>
                <div class="text">制单人</div>
                <div class="num">{{info.createTime}}</div>
                <div class="text">创建日期</div>
                <div class="num">{{info.createUserName}}</div>
            </div>
            <div class="info-area" >
                <div class="text">备注</div>
                <div class="num">{{info.remarks}}</div>
            </div>
        </el-card>
    </el-dialog>
</template>

<script>
    export default {
        name: "location-detail",
        props: {
            visible: Boolean,
            id:String
        },
        data() {
            return {
                info:{},
                stationScheduleList:[]
            }
        },
        watch:{
            visible(newval,oldval){
                if(newval){
                    this.getStationSchedule();

                }
            }
        },
        methods: {
            getData(data){
                this.$axios.get('/threeEstimate/findFixedMeasureById/'+this.id).then(res=>{
                    this.info=res.data;
                    var column=this.info.column;
                    var stationScheduleList=[]
                    for(var i in column){
                        for(var j in data){
                            if(column[i].id==data[j].id){
                                stationScheduleList.push({
                                    stationScheduleValue:column[i].stationScheduleValue,
                                    id:column[i].id,
                                    stationIntervalName:column[i].stationIntervalName
                                })
                            }
                        }
                    }
                    this.stationScheduleList=stationScheduleList;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            getStationSchedule(){
                this.$axios.get('/stationSchedule/findStationSchedule/'+this.$root.getStorage('projectId')).then(res => {
                    this.getData(res.data.list);
                }).catch(err => {
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
    .info-area-2{
        display: grid;
        grid-template-columns: repeat(3,33.33%);
        >div{
            margin-bottom: 20px;
            display: flex;
            flex-wrap: nowrap;
        }
        .text {
            /*text-align: right;*/
            margin-right: 20px;
        }
        .num {
            text-align: left;
            padding:0 10px;
            flex: 1;
            background: #f5f7fa;
            color: #c6c6c6;
            margin-right: 10px;
        }
    }
    .info-area{
        display: flex;
        flex-wrap: nowrap;
        width: 100%;
        font-size: 14px;
        margin-top: 10px;
        >div{
            margin-bottom: 20px;
        }
        .text {
            /*text-align: right;*/
            margin-right: 20px;
        }
        .num {
            text-align: left;
            padding:0 10px;
            background: #f5f7fa;
            color: #c6c6c6;
            margin-right: 10px;
            flex: 1;
        }
    }
</style>