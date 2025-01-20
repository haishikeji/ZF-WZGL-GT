<template>
	<view>
		<view class="select flex-n fac">
			<u-input class="input" type="select" clearable :border="border" :height="height" :input-align="align" :value="value"  :placeholder="placeholder" @click="$emit('click')" :clearable="false"/>
		</view>
	</view>
</template>

<script>
	export default {
		name:"z-select",
		props:{
			value:String,
			placeholder:String,
			height:{
				type:String,
				default:'95'
			},
			border:{
				type:Boolean,
				default:true
			},
			align:String,
			clearable:{
				type:Boolean,
				default:true
			},
			mode:String,//mutil-column-auto
		},
		model:{
			prop:'value',
			event:'input'
		},
		data() {
			return {
				text:'',
			};
		},
		// watch:{
		// 	value(newval,oldval){
		// 		this.text=this.value;
		// 	}
		// },
		// mounted(){
		// 	this.text=this.value;
		// },
		methods:{
			
			delect(){
				// this.text='';
				this.$emit('input','')
				this.$emit('delect');
			}
		}
	}
</script>

<style scoped lang="scss">
.select{
		position: relative;
		width: 100%;
		.close{
			position: absolute;
			width: 28rpx;
			right: 20rpx;
			z-index: 100;
		}
		.close-1{
			width: 28rpx;
			margin-left: 20rpx;
		}
	}
</style>
