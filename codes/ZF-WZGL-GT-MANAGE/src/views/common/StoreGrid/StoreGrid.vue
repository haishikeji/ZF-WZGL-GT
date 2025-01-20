<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            append-to-body
            width="1200px"
    >
        <div class="location-grid" v-if="list.length>0" >
            <div class="location-grid-content" :style="{'grid-template-columns': '30px repeat('+columnNumber+',calc((100% - 30px) / '+columnNumber+'))','grid-template-rows': '30px repeat('+rowNumber+', 150px)'
}">
                <div style="grid-column-start:1;grid-column-end:2"></div>
                <div class="location-grid-letter" :style="{'grid-column-start': index+2,
  'grid-column-end':index+3}" v-for="(item,index) in letterList" :key="index">{{item}}
                </div>
                <div class="location-grid-num" :style="{'grid-row-start':i+1,'grid-row-end':i+2}"
                     v-for="i in rowNumber">{{i}}
                </div>
                <div class="location-grid-item" :style="{background:item.status==0?'#f1f1f1':'white',cursor:item.status==0?'not-allowed':'pointer','outline':choose==index?'1px solid #2bf137':''}" v-for="(item,index) in list" :key="item.id" @click="item.status==0?'':chooseLocation(item,index)" >
                    <div class="location-grid-item-pos">{{item.gridNo}}</div>
                    <div class="location-grid-item-num">{{item.gridName}}</div>
                </div>
            </div>

        </div>
        <span slot="footer" class="dialog-footer">
                 <el-button size="medium" @click="resetForm">取 消</el-button>
                <el-button size="medium" type="primary" @click="select">选 择</el-button>
            </span>
    </el-dialog>
</template>

<script>
    export default {
        name: "store-grid",
        props: {
            id: String,
            visible:Boolean,
            gridId:String
        },
        data() {
            return {
                list: [],
                letterList: [],
                columnNumber:0,
                rowNumber:0,
                info:{},
                choose:-1,
            }
        },
        watch:{
            visible(newval,oldval){
                if(newval){
                    this.getPositionInfo();
                }
            }
        },
        methods: {
            getPositionInfo() {
                this.$axios.post('/storeGrid/findByStoreId', {
                    "storeId": this.id,
                }).then(res => {
                    var list = [];
                    console.log(res.data, res.data.list)
                    for (var i in res.data.list) {
                        list.push(res.data.list[i]);
                        if(this.gridId==res.data.list[i].id){
                            this.choose=i;
                        }
                    }
                    this.list = list;

                    this.getGridCount();
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            //画网格
            getGridCount(list) {
                this.$axios.get('/storeGrid/gridCount/' + this.id).then(res => {
                    console.log('网格行列', res.data)
                    var num = Number(res.data.columnCount);
                    this.columnNumber=num;
                    this.rowNumber=res.data.rowCount;
                    var letterList = [];
                    for (var i = 0; i < num; i++) {
                        letterList.push(String.fromCharCode(65 + i))
                    }
                    this.letterList = letterList;
                }).catch(err => {
                    this.$message.error(err.msg)
                })
            },
            chooseLocation(info,index) {
                console.log(info)
                this.choose=index;
                this.info=info;
            },
            resetForm() {
                this.$emit('update:visible', false);
            },
            select(){
                this.$emit('change',this.info);
                this.resetForm();
            }
        }
    }
</script>

<style scoped lang="scss">
    .location-grid {
        overflow-x: scroll;
        background: #d6d6d6;
        border: 1px solid #d6d6d6;
        .location-grid-content {
            background: #d6d6d6;
            display: grid;
            grid-gap: 1px;
            width: auto;

            .location-grid-letter {

                line-height: 30px;
                grid-row-start: 1;
                grid-row-end: 2;
            }
            .location-grid-num {
                grid-column-start: 1;
                grid-column-end: 2;
                line-height: 150px;
            }
            .location-grid-letter, .location-grid-num {
                text-align: center;
                background: #f5f7fa;
                font-weight: bold;
                color: #777;
            }
            .location-grid-item {
                text-align: center;
                cursor: pointer;
                background: white;
                .location-grid-item-pos {
                    margin-top: 30px;
                    font-size: 40px;
                }
                .location-grid-item-num {
                    margin-top: 10px;

                }
            }
            div {
                /*background: white;*/
            }
        }
    }
</style>