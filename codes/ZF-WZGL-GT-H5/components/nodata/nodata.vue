<template>
	<view>
		<image class="nodata" mode="widthFix" src="../../common/img/nodata.png"></image>
	</view>
</template>

<script>
	export default {
		name:"nodata",
		data() {
			return {
				
			};
		}
	}
</script>

<style scoped lang="scss">
.nodata {
		width: 571rpx;
		margin: 0 calc((100% - 571rpx) / 2);
		margin-top: 289rpx;
	}
</style>
