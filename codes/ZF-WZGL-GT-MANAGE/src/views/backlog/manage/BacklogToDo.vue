<template>
    <div>

        <z-title title="待办管理" icon="el-icon-s-cooperation"></z-title>
        <content-card>
            <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
                <el-tab-pane label="我的申请" name="/backlog/manage/apply"></el-tab-pane>
                <el-tab-pane label="待我审核" name="/backlog/manage/wait"></el-tab-pane>
                <el-tab-pane label="经我审核" name="/backlog/manage/after"></el-tab-pane>
                <el-tab-pane label="审核完成" name="/backlog/manage/complete"></el-tab-pane>
                <!--通用搜索-->
                <div class="flex-n fac">
                    <div class="comm-label">流程标题</div>
                    <el-input v-model="workflow.taskNodeName" class="w-220" clearable size="mini" placeholder="请输入流程标题"></el-input>
                    <div class="comm-label ml-10">申请时间</div>
                    <el-date-picker
                            v-model="workflow.dateRange"
                            size="mini"
                            value-format="yyyy-MM-dd"
                            type="daterange"
                            clearable
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期">
                    </el-date-picker>
                    <el-button size="mini" type="primary" class="ml-10" @click="searchList">查询</el-button>
                    <el-button size="mini" class="ml-10" @click="resetData">重置</el-button>
                </div>
                <!--子路由-->
                <router-view></router-view>
            </el-tabs>
        </content-card>
    </div>
</template>

<script>
    import ZTitle from "../../../components/ZTitle/ZTitle";
    import ContentCard from "../../../components/ContentCard/ContentCard";
    import {mapActions, mapState,mapGetters} from "vuex";

    export default {
        components: {
            ContentCard,
            ZTitle},
        name: "backlog-to-do",
        props:{

        },
        data(){
            return{
                taskNodeName:'',
                dateRange:[],
                activeName:'/backlog/manage/apply'
            }
        },
        computed:{
            //数据copy
            ...mapState("workflow",{
                workflow:state=>state
            })
        },
        watch:{
            $route(){
                this.activeName=this.$route.path;
            }
        },
        mounted(){
            this.activeName=this.$route.path;
        },
        methods:{
            searchList(){
                this.$store.state.workflow.search=true;
            },
            handleClick(tab, event) {
                console.log(tab, event);
                this.$router.push({path:tab.name});
            },
            resetData(){
                this.$store.commit('workflow/delData');
                this.searchList();
            }
        }
    }
</script>

<style scoped lang="scss">

</style>