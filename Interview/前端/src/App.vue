<template>
  <div class="min-h-screen flex flex-col relative">
    <!-- 导航栏 -->
    <header class="sticky top-0 z-50 bg-white/70 backdrop-blur-xl border-b border-white/50 shadow-[0_4px_30px_rgba(0,0,0,0.03)]">
      <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <div class="flex items-center space-x-4">
          <div class="w-12 h-12 rounded-2xl bg-gradient-to-br from-rose-300 to-sky-300 flex items-center justify-center shadow-lg shadow-rose-300/20 transform hover:scale-105 transition-transform duration-300">
            <i class="fa fa-comments text-white text-xl"></i>
          </div>
          <div>
            <h1 class="text-xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-slate-800 to-slate-600">智能模拟面试</h1>
            <p class="text-xs font-medium text-rose-400 mt-0.5 tracking-wide">AI 驱动的面试训练平台</p>
          </div>
        </div>
        <nav>
          <ul class="flex items-center space-x-2">
            <li>
              <router-link 
                to="/" 
                class="px-5 py-2.5 rounded-xl text-slate-600 hover:text-rose-600 hover:bg-rose-50/80 transition-all duration-300 font-medium flex items-center gap-2"
                active-class="text-rose-600 bg-rose-50 shadow-sm shadow-rose-100"
              >
                <i class="fa fa-home"></i>首页
              </router-link>
            </li>
            <li>
              <router-link 
                to="/interview" 
                class="px-5 py-2.5 rounded-xl text-slate-600 hover:text-rose-600 hover:bg-rose-50/80 transition-all duration-300 font-medium flex items-center gap-2"
                active-class="text-rose-600 bg-rose-50 shadow-sm shadow-rose-100"
              >
                <i class="fa fa-play-circle"></i>开始面试
              </router-link>
            </li>
            <li class="ml-4 pl-4 border-l border-slate-200" v-if="!isLoggedIn">
              <router-link 
                to="/login" 
                class="gradient-btn py-2.5 px-6 text-sm flex items-center gap-2"
              >
                <i class="fa fa-user-circle"></i>登 录
              </router-link>
            </li>
            <li class="ml-4 pl-4 border-l border-slate-200" v-else>
              <el-dropdown trigger="click" @command="handleCommand">
                <button class="gradient-btn py-2.5 px-6 text-sm flex items-center gap-2 outline-none">
                  <i class="fa fa-user-circle"></i> 我的
                  <i class="fa fa-caret-down ml-1"></i>
                </button>
                <template #dropdown>
                  <el-dropdown-menu class="w-36 rounded-xl shadow-xl border-none">
                    <el-dropdown-item command="report" class="py-3 font-medium text-slate-700 hover:text-rose-600">
                      <i class="fa fa-file-text-o mr-2"></i>面试报告
                    </el-dropdown-item>
                    <el-dropdown-item divided command="logout" class="py-3 font-medium text-red-500 hover:text-red-600">
                      <i class="fa fa-sign-out mr-2"></i>退出登录
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </li>
          </ul>
        </nav>
      </div>
    </header>
    
    <!-- 主内容区 -->
    <main class="flex-grow container mx-auto px-4 py-8 relative z-10">
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>
    
    <!-- 页脚 -->
    <footer class="bg-slate-900 text-white py-12 relative z-10 overflow-hidden">
      <!-- Footer decorative background -->
      <div class="absolute top-0 right-0 w-96 h-96 bg-rose-300/15 rounded-full blur-3xl translate-x-1/2 -translate-y-1/2 pointer-events-none"></div>
      
      <div class="container mx-auto px-6">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-12 mb-12">
          <div>
            <div class="flex items-center space-x-4 mb-6">
              <div class="w-12 h-12 rounded-xl bg-gradient-to-br from-slate-700 to-slate-900 flex items-center justify-center">
                <i class="fa fa-comments text-white text-xl"></i>
              </div>
              <span class="text-xl font-bold">智能模拟面试</span>
            </div>
            <p class="text-slate-400 text-sm leading-loose max-w-sm">
              基于前沿 AI 技术的智能面试训练平台，通过多维度分析帮助求职者快速提升面试技巧，轻松拿下心仪 Offer。
            </p>
          </div>
          <div>
            <h4 class="font-semibold text-lg mb-6 text-slate-100">核心功能</h4>
            <ul class="space-y-3 text-slate-400 text-sm">
              <li class="flex items-center gap-3 hover:text-rose-300 transition-colors cursor-pointer">
                <i class="fa fa-check-circle text-rose-400"></i>AI 深度解析 JD
              </li>
              <li class="flex items-center gap-3 hover:text-rose-300 transition-colors cursor-pointer">
                <i class="fa fa-check-circle text-rose-400"></i>多维度智能出题
              </li>
              <li class="flex items-center gap-3 hover:text-rose-300 transition-colors cursor-pointer">
                <i class="fa fa-check-circle text-rose-400"></i>全方位评分分析
              </li>
              <li class="flex items-center gap-3 hover:text-rose-300 transition-colors cursor-pointer">
                <i class="fa fa-check-circle text-rose-400"></i>个性化改进建议
              </li>
            </ul>
          </div>
          <div>
            <h4 class="font-semibold text-lg mb-6 text-slate-100">联系我们</h4>
            <ul class="space-y-3 text-slate-400 text-sm">
              <li class="flex items-center gap-3 hover:text-rose-300 transition-colors cursor-pointer">
                <div class="w-8 h-8 rounded-lg bg-slate-800 flex items-center justify-center"><i class="fa fa-envelope"></i></div>
                support@interview.ai
              </li>
              <li class="flex items-center gap-3 hover:text-rose-300 transition-colors cursor-pointer">
                <div class="w-8 h-8 rounded-lg bg-slate-800 flex items-center justify-center"><i class="fa fa-github"></i></div>
                GitHub Repository
              </li>
              <li class="flex items-center gap-3 hover:text-rose-300 transition-colors cursor-pointer">
                <div class="w-8 h-8 rounded-lg bg-slate-800 flex items-center justify-center"><i class="fa fa-weixin"></i></div>
                官方微信公众号
              </li>
            </ul>
          </div>
        </div>
        <div class="border-t border-slate-800 pt-8 text-center text-slate-500 text-sm flex flex-col md:flex-row justify-between items-center gap-4">
          <p>© 2026 智能模拟面试系统 | 保留所有权利</p>
          <div class="flex gap-4 text-xs">
            <a href="#" class="hover:text-white transition-colors">隐私政策</a>
            <a href="#" class="hover:text-white transition-colors">服务条款</a>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const isLoggedIn = computed(() => !!localStorage.getItem('token'))

const handleCommand = (command) => {
  if (command === 'report') {
    router.push('/report')
  } else if (command === 'logout') {
    localStorage.removeItem('token')
    sessionStorage.removeItem('latestInterviewReport')
    window.location.reload()
  }
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease, transform 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(10px);
}
</style>
