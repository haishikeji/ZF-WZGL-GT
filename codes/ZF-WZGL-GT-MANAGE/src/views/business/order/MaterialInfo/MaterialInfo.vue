<template>
    <div>
        <el-card shadow="never" class="box-card mt-10">
            <div slot="header" class="flex-n fac fjb">
                <div class="comm-card-title-one">物资信息</div>
                <div class="flex-n fac">
                    <slot></slot>
                </div>
            </div>
            <el-table :data="tableData" cell-class-name="cell_table" v-loading="loading" show-overflow-tooltip
                highlight-current-row :height="height + 'px'" element-loading-text="加载中" style="width: 100%">
                <!-- <el-table-column v-for="(item, index) in rowName2" :key="index" show-overflow-tooltip :width="item.width"
                    :prop="item.prop" :label="item.label" :fixed="item.fixed ? item.fixed : false">
                    <template slot-scope="scope">
                        <template v-if="item.prop2 == 'num' ">
                            <el-input-number  class="w-90" v-model="scope.row[item.prop]" size="mini" :controls="false"
                                :min="0.0001"  >   </el-input-number >
                        </template>
                        <template v-else>
                            {{ scope.row[item.prop] }}
                        </template>
                    </template>
                </el-table-column> -->
                <el-table-column v-for="(item, index) in rowName2" :key="index" :min-width="item.width || 100"
                    :prop="item.prop" :label="item.label" :fixed="item.fixed ? item.fixed : false">
                    <template slot-scope="scope">
                        <template v-if="item.prop2 == 'num'">
                            <el-input-number style="min-width: 0; width: 90%" v-model="scope.row[item.prop]" size="mini"
                                :controls="false" :min="0.0001">
                            </el-input-number>
                        </template>
                        <template v-else>
                            {{ scope.row[item.prop] }}
                        </template>
                    </template>
                </el-table-column>
                <template >
                    <el-table-column width="80" label="操作" fixed="right">
                        <template slot-scope="scope">
                            <el-popconfirm title="该操作将删除该物资数据,确定要删除吗" @confirm="delOrder(scope.$index)">
                                <el-button slot="reference" type="text">删除</el-button>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </template>

            </el-table>
        </el-card>
    </div>
</template>

<script>

export default {
    name: "material-info",
    props: {
        tableData: Array,
        rowName2: Array,
        type: {
            type: String | Number,
            default: 0
        },
        state: {//库存编辑状态
            type: String | Number,
            default: 0
        },
        storeId: String,
        selectPlan: {
            type: String | Number,
            default: 1
        }
    },
    model: {
        prop: 'tableData',
        event: 'input'
    },
    data() {
        return {
            height: 500,
            loading: false,
            idx: 0,
            showGrid: false,
            gridId: '',

            storeList: [],
            rowName: [],
            id: ''
        }
    },
    mounted() {
        console.log(123)
        this.getStoreList();
    },
    watch: {
            visible(newval, oldval) {
            console.log(123)
                if (newval) {
                    this.getRowName();
                }
            },
            rowName2(newval, oldval) {
            if(this.height==499.5){
                 this.height=500;
            }else{
                this.height=499.5;
            }
            if (newval) {
                this.getRowName();
            }
        }
        },
    methods: {
        getStoreList() {
          this.getRowName();
        },
        formattedNumber(number) {
            return number.toFixed(4);
        },
        delOrder(idx) {
            this.tableData.splice(idx, 1);
        },

        getRowName() {
            console.log("1111111111121212121")
            var rowName= [{
                label: '物资编号',
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
            }, {
                label: '物资类型',
                prop: 'typeName',
                width: '',
                fixed: ''
            }, {
                label: '计量单位',
                prop: 'measureUnit',
                width: '80',
                fixed: ''
            }]
            
           
        },
    }
}
</script>

<style scoped></style>