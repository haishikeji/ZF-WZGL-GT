<template>
	<view>
		<view class="flex-n z-steps" v-for="(item,index) in list" :key="index"  :class="[index!=list.length-1?'step-line-default':'',((type=='success'?(step>index):(step-1>index))&&index!=list.length-1)?'step-line':'']">
			<view class="left">
				<image class="img"></image>
				<image class="icon" v-if="index<step-1" src="../../common/img/pending/select.png"></image>
				<image class="icon" v-if="index==step-1" :src="type=='success'?require('../../common/img/pending/select.png'):require('../../common/img/pending/close3.png')"></image>
			</view>
			<view class="right flex-1">
				<view class="right-top flex-n fjb">
					<template v-if="type=='success'">
						<view class="title title-finish" v-if="index<step">{{item.title}}</view>
						<view class="title title-wait" v-else-if="index==step">{{item.title}}</view>
						<view class="title " v-else>{{item.title}}</view>
					</template>
					<template v-else>
						<view class="title title-finish" v-if="index<step-1">{{item.title}}</view>
						<view class="title title-fail" v-else-if="index==step-1">{{item.title}}</view>
						<view class="title " v-else>{{item.title}}</view>
					</template>
					
					<view class="time" v-if="item.time">{{item.time}}</view>
				</view>
				<view class="subtitle" v-if="item.subtitle" :class="[index<step?'subtitle-select':'']">{{item.subtitle}}</view>
				<view class="content" v-if="item.content">
					{{item.content}}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name:"z-steps",
		props:{
			// title:String,
			// subTitle:String,
			// time:String,
			// content:String,
			// image:String,
			type:{
				default:'success',//当前操作显示颜色 ，拒绝fail
				type:String
			},
			//在第几步
			step:{
				default:0,//如果步数等于list的长度，则最后一个是完成，显示完成样式，绿色
				type:Number|String
			},
			//数据
			list:{
				default:null,
				type:Array
			},
		},
		data() {
			return {
				
			};
		}
	}
</script>

<style scoped lang="scss">
	.step-line-default{
		position: relative;
		&:before{
			position: absolute;
			left: 37rpx;
			top:90rpx;
			content: '';
			width: 6rpx;
			background: #e5e5e5;
			height: calc(100% - 30rpx);
		}
	}
	.step-line{
		position: relative;
		&:before{
			position: absolute;
			left: 37rpx;
			top:90rpx;
			content: '';
			width: 6rpx;
			background: #1677FF;
			height: calc(100% - 30rpx);
		}
	}
	.z-steps{
		margin-bottom: 70rpx;
		.left{
				position: relative;
				width: 80rpx;
				height: 80rpx;
				.img{
					width: 80rpx;
					height: 80rpx;
					border-radius: 50%;
					overflow: hidden;
					background: #f5f5f5;
				}
				.icon{
					position: absolute;
					width: 40rpx;
					height: 40rpx;
					right: 0;
					bottom: 0;
				}
			}
		.right{
			margin-left: 24rpx;
			.right-top{
				.title{
					font-size: 34rpx;
					color: #aaa;
					letter-spacing: 0.58rpx;
					font-weight: 600;
				}
				.title-wait{
					color: #FF8616!important;
				}
				.title-fail{
					color: #E02020!important;
				}
				.title-finish{
					color: #1677FF!important;
				}
				.time{
					font-size: 28rpx;
					color: #999999;
					letter-spacing: 0.58rpx;
				}
			}
			.subtitle{
				font-size: 34rpx;
				color: #ccc;
				letter-spacing: 0.58rpx;
				font-weight: 600;
			}
			.subtitle-select{
				color: #333;
			}
			.content{
				background: #F5F5F5;
				border-radius: 16rpx;
				padding: 30rpx 16rpx;
				margin-top: 38rpx;
			}
		}
		
	}

</style>
