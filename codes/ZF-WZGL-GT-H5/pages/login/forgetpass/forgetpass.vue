<template>
	<view>

		<u-navbar back-icon-color="#1677FF" title="忘记密码" title-size="36"></u-navbar>
		<template v-if="step==0">
			<view class="login-input">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['border']">
					<u-form-item label="" prop="tel" :border-bottom="false">
						<u-input height="65" v-model="form.tel" placeholder="请输入手机号" />
					</u-form-item>
				</u-form>
			</view>
			<z-button type="primary" class="mt-54" @click="next(1)">下一步</z-button>
		</template>
		<template v-if="step==1">
			<view @click="show=true">
				<view class="tips">
					<view>验证码将发送到绑定手机号</view>
					<view>136****2749</view>
				</view>
				<u-message-input @finish="finish" disabled-keyboard :value="value"></u-message-input>
				<u-keyboard ref="uKeyboard" mode="number" :dot-enabled="false" v-model="show" @change="valChange"
					@backspace="backspace"></u-keyboard>
			</view>
			<view class="mt-54 flex-n fjb plr-24">
				<z-button width="339rpx" @click="before(0)">上一步</z-button>
				<z-button width="339rpx" type="primary" @click="next(2)">下一步</z-button>
			</view>
		</template>
		<template v-if="step==2">
			<view class="login-input">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['border']">
					<u-form-item label="" prop="userName">
						<u-input height="65" v-model="form.userName" placeholder="请输入新密码" />
					</u-form-item>
					<u-form-item label="" prop="passWord" :border-bottom="false">
						<u-input height="65" type="password" v-model="form.passWord" placeholder="再次输入新密码"
							:clearable="false" />
					</u-form-item>
				</u-form>
			</view>
			<view class="mt-54 flex-n fjb plr-24">
				<z-button width="339rpx" @click="before(1)">上一步</z-button>
				<z-button width="339rpx" type="primary" @click="modify">确认密码</z-button>
			</view>
		</template>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				step: 0,
				show: true,
				value: '',
				form: {
					userName: '',
					passWord: ''
				},
				rules: {
					// name: [{
					// 	min: 6,
					// 	max: 8,
					// 	message: '长度在6-8个字符之间'
					// }, {
					// 	required: true,
					// 	message: '请输入姓名',
					// 	// 可以单个或者同时写两个触发验证方式 
					// 	trigger: ['change', 'blur'],
					// }]
				},
			}
		},

		methods: {
			// 按键被点击(点击退格键不会触发此事件)
			valChange(val) {
				// 将每次按键的值拼接到value变量中，注意+=写法
				this.value += val;
				console.log(this.value);
			},
			// 退格键被点击
			backspace() {
				// 删除value的最后一个字符
				if (this.value.length) this.value = this.value.substr(0, this.value.length - 1);
				console.log(this.value);
			},
			onReady() {
				// 如果想一进入页面就打开键盘，请在此生命周期调用
				this.show = true;
			},
			next(step) {
				this.step = step;
			},
			before(step) {
				this.step = step;
			},
			modify() {
				uni.redirectTo({
					url: '../login',
					success: res => {},
					fail: () => {},
					complete: () => {}
				});
			},
			finish(e) {
				console.log('输入结束，当前值为：' + e);
			},
		}
	}
</script>
<style>
	page {
		background: white;
	}
</style>
<style scoped lang="scss">
	.login-input {
		border-top: 1px solid #eeeeee;
		border-bottom: 1px solid #eeeeee;
		padding: 0 24rpx;
		margin-top: 93rpx;
	}

	.tips {
		font-size: 36rpx;
		color: #888888;
		margin-top: 93rpx;
		text-align: center;
	}
</style>
