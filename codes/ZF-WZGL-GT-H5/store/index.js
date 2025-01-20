import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const store = new Vuex.Store({
    state: {
		dicChange:false,
		tabbar:[{
					iconPath: "/static/tabbar-1.png",
					selectedIconPath: "/static/tabbar-1-select.png",
					text: '首页',
					// count: 2,
					// isDot: true,
					pagePath: "/pages/index/index/index"
				},
				{
					iconPath: "/static/tabbar-2.png",
					selectedIconPath: "/static/tabbar-2-select.png",
					text: '待处理',
					pagePath: "/pages/pending/index/index"
				},
				{
					iconPath: "/static/tabbar-3.png",
					selectedIconPath: "/static/tabbar-3-select.png",
					text: '我的消息',
					pagePath: "/pages/news/index/index"
				},
				{
					iconPath: "/static/tabbar-4.png",
					selectedIconPath: "/static/tabbar-4-select.png",
					text: '我的',
					pagePath: "/pages/center/index/index"
				},
			]
	},
    mutations: {},
    actions: {}
})
export default store