import Vue from 'vue'
Vue.directive('pointer',{
    //,binding,vnode
    bind:function (el) {
        el.style.cursor='pointer';
    }
})