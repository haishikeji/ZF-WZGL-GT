export default{
    namespaced: true,//局部调用
    //装卸计划统计
    state:{
        taskNodeName:'',//货物名称
        dateRange:[],
        search:false
    },
    mutations: {
        delData(state){
            state.taskNodeName='';
            state.dateRange=[];
        }
    },
}