<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            append-to-body
            width="1200px"
    >
        <div slot="title" class="comm-toast-title-one">定测量详情</div>

        <el-card shadow="never">

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
                        width="100"
                        prop="projectName"
                        label="项目名称">
                </el-table-column>
                <el-table-column
                        width="100"
                        prop="goodCode"
                        label="物资编号">
                </el-table-column>
                <el-table-column
                        width="100"
                        prop="goodName"
                        label="物资名称">
                </el-table-column>
                <el-table-column
                        width="80"
                        prop="specsModelName"
                        label="规格型号">
                </el-table-column>
                <el-table-column
                        width="80"
                        prop="unit"
                        label="单位">
                </el-table-column>
                <el-table-column
                        min-width="100"
                        v-for="(item,index) in stationScheduleList" :key="index"
                        :prop="item.stationIntervalName"
                        :label="item.stationIntervalName">
                </el-table-column>
                <el-table-column
                        prop="total"
                        label="合计">
                </el-table-column>
                <el-table-column
                        width="200"
                        prop="remarks"
                        label="备注">
                </el-table-column>
            </el-table>
        </el-card>
    </el-dialog>
</template>

<script>
    export default {
        name: "look-measure-detail",
        props: {
            visible: Boolean,
            id:String,
            sceneId:String
        },
        data() {
            return {
                loading: false,
                height:450,
                tableData: [],
                stationScheduleList:[],
            }
        },
        watch:{
            visible(newval,oldval){
                if(newval){
                    this.getStationSchedule();
                    this.getData();
                }
            }
        },
        methods: {
            getStationSchedule(){
                this.$axios.get('/stationSchedule/findStationSchedule/'+this.$root.getStorage('projectId')).then(res => {
                    this.stationScheduleList = res.data.list;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
            },
            getData(){
                this.$axios.get('/workflow/findDetailById/'+this.id+'/'+this.sceneId+'/'+this.$root.getStorage('projectId')).then(res=>{
                    console.log(res.data);
                    this.tableData=res.data.list;

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
        grid-template-columns: repeat(4, 10% 15%);
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