<template>
    <div>
       <div class="z-title flex-n fac fjb">
           <div class="flex-n fac">
           <div class="icon" :class="icon"></div>
           <div>{{title}}</div>
           </div>
           <div class="right-content">
           <slot></slot>
           </div>
       </div>
    </div>
</template>

<script>
    export default {
        name: "z-title",
        props:{
            title:String,
            icon:String
        }
    }
</script>

<style scoped lang="scss">
.z-title{
    padding:15px 20px;
    font-weight: 600;
    padding-right: 50px;
    color: #5e5f5f;
    background: white;
    box-shadow: 0 0 10px #eee;
    width: calc(100% - 40px);
    z-index: 200;
    .icon{
        margin-right: 10px;
        font-size: 20px;
    }
    .right-content{
        position: absolute;
        right: 20px;
    }
}
</style>