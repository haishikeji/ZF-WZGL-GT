<template>
	<view>
		<u-mask :show="visible" @click="hide">
			<view class="operation-content flex-n fac fjc">
				<view class="content-box" @tap.stop></view>
			</view>
		</u-mask>
	</view>
</template>

<script>
	export default {
		name:"operation",
		props:{
			visible:Boolean
		},
		data() {
			return {
				
			};
		},
		methods:{
			hide(){
				this.$emit('update:visible',false);
			}
		}
	}
</script>

<style scoped lang="scss">
.operation-content{
	height: 100%;
	.content-box{
			width: 120px;
				height: 120px;
				background-color: #fff;
	}
}
</style>
