<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="900px"
    >
        <div slot="title" class="comm-toast-title-one">查看详情</div>
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
        name: "location-detail",
        props: {
            visible: Boolean,
            id:String
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
                    },  {
                        label: '物资类别',
                        prop: 'typeName',
                        width: '100',
                        fixed: ''
                    }, {
                        label: '计量单位',
                        prop: 'measureUnit',
                        width: '80',
                        fixed: ''
                    }, {
                        label: '剩余库存',
                        prop: 'stockNum',
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
              this.$axios.post('/report/stockGridList',{
                  "gridId": this.id
              }).then(res=>{
                 console.log(res.data)
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
   
</style>