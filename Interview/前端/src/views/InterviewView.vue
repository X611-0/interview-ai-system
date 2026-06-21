<template>
  <div class="max-w-4xl mx-auto py-6">
    <!-- 加载状态 - 生成问题中 -->
    <div v-if="loading" class="text-center py-24 glass-card">
      <div class="inline-flex items-center justify-center w-24 h-24 rounded-3xl bg-gradient-to-br from-rose-300 to-sky-300 shadow-xl shadow-rose-300/20 mb-8 animate-pulse relative">
        <div class="absolute inset-0 rounded-3xl border-4 border-white/20 animate-ping opacity-50"></div>
        <i class="fa fa-spinner fa-spin text-white text-4xl"></i>
      </div>
      <h3 class="text-2xl font-bold text-slate-800 mb-3">AI 正在生成您的专属面试题库</h3>
      <p class="text-slate-500 text-lg">正在基于您的简历和JD准备 {{ questionCount }} 道定制问题，请稍候...</p>
    </div>

    <!-- 面试答题中 -->
    <div v-else-if="!interviewFinished && questions.length > 0" class="animate-fade-in">
      <!-- 进度条 -->
      <div class="glass-card p-6 mb-8 relative overflow-hidden">
        <div class="absolute right-0 top-0 w-32 h-32 bg-rose-300/15 rounded-full blur-2xl -translate-y-1/2 translate-x-1/2"></div>
        <div class="flex items-center justify-between mb-4">
          <div class="flex items-center gap-4">
            <div class="w-12 h-12 rounded-2xl bg-gradient-to-br from-rose-300 to-sky-300 flex items-center justify-center shadow-md">
              <i class="fa fa-microphone text-white text-lg"></i>
            </div>
            <div>
              <p class="text-lg font-bold text-slate-800">模拟面试进行中</p>
              <p class="text-sm text-slate-500">请尽量使用语音输入，还原真实面试体验</p>
            </div>
          </div>
          <div class="text-right">
            <p class="text-3xl font-extrabold text-gradient">{{ currentIndex + 1 }}<span class="text-lg text-slate-400 font-medium">/{{ questions.length }}</span></p>
          </div>
        </div>
        <div class="h-2.5 bg-slate-100 rounded-full overflow-hidden shadow-inner">
          <div 
            class="h-full bg-gradient-to-r from-rose-300 via-amber-200 to-sky-300 rounded-full transition-all duration-700 ease-out relative"
            :style="{ width: ((currentIndex + 1) / questions.length * 100) + '%' }"
          >
            <div class="absolute top-0 right-0 bottom-0 left-0 bg-[linear-gradient(45deg,rgba(255,255,255,0.2)_25%,transparent_25%,transparent_50%,rgba(255,255,255,0.2)_50%,rgba(255,255,255,0.2)_75%,transparent_75%,transparent)] bg-[length:1rem_1rem] animate-[progress_1s_linear_infinite]"></div>
          </div>
        </div>
      </div>
      
      <!-- 当前问题 -->
      <div class="glass-card p-10 mb-8 border-t-4 border-t-rose-300">
        <div class="flex items-start justify-between mb-8">
          <div class="flex items-center gap-4">
            <div class="w-14 h-14 rounded-2xl bg-gradient-to-br from-rose-50 to-sky-50 border border-rose-100 flex items-center justify-center text-rose-600 font-black text-2xl shadow-sm">
              Q{{ currentIndex + 1 }}
            </div>
            <div>
              <h3 class="text-xl font-bold text-slate-800">面试官提问</h3>
              <div class="mt-2">
                <span class="tag-primary">{{ currentQuestion.category }}</span>
              </div>
            </div>
          </div>
        </div>
        
        <div class="bg-gradient-to-r from-slate-50 to-rose-50/50 rounded-2xl p-8 mb-8 border border-slate-100 shadow-sm relative">
          <i class="fa fa-quote-left absolute top-4 left-4 text-3xl text-rose-400/10"></i>
          <p class="text-xl text-slate-700 leading-loose font-medium relative z-10 pl-6">
            {{ currentQuestion.question }}
          </p>
        </div>
        
        <div class="space-y-5">
          <div class="flex items-center justify-between">
            <label class="block text-base font-bold text-slate-700">
              <i class="fa fa-commenting mr-2 text-rose-500"></i>您的回答
            </label>
            
            <!-- 语音输入按钮 -->
            <button 
              @click="toggleSpeechRecognition"
              class="flex items-center gap-2 px-5 py-2.5 rounded-xl font-bold transition-all duration-300"
              :class="isRecording 
                ? 'bg-red-50 text-red-600 border border-red-200 shadow-[0_0_15px_rgba(239,68,68,0.3)] animate-pulse' 
                : 'bg-white text-slate-600 border border-slate-200 hover:border-rose-200 hover:text-rose-600 hover:bg-rose-50 shadow-sm'"
            >
              <i :class="isRecording ? 'fa fa-stop-circle text-lg' : 'fa fa-microphone text-lg'"></i>
              {{ isRecording ? '结束录音' : '开启语音答题' }}
            </button>
          </div>
          
          <div class="relative">
            <textarea 
              v-model="answer"
              rows="8"
              placeholder="请在此输入您的回答，或点击右上角使用语音输入..."
              class="w-full px-6 py-5 rounded-2xl border-2 border-slate-200/80 bg-white/50 backdrop-blur
                     focus:border-rose-300 focus:ring-4 focus:ring-rose-200/40 focus:outline-none focus:bg-white
                     transition-all duration-300 text-slate-700 resize-none shadow-sm text-lg leading-relaxed"
            ></textarea>
            
            <div v-if="isRecording" class="absolute bottom-4 right-4 flex items-center gap-2 bg-red-100/80 backdrop-blur px-3 py-1.5 rounded-full text-red-600 text-sm font-medium border border-red-200">
              <div class="w-2 h-2 rounded-full bg-red-500 animate-ping"></div>
              正在倾听...
            </div>
          </div>
          
          <div class="flex items-center justify-between pt-4">
            <span class="text-sm font-medium text-slate-400 bg-slate-100 px-3 py-1 rounded-full">
              <i class="fa fa-text-width mr-1"></i>
              {{ answer.length }} 字符
            </span>
            
            <button 
              @click="submitAnswer" 
              :disabled="!answer.trim() || isRecording"
              class="gradient-btn px-8 flex items-center gap-2 disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none shadow-lg text-lg"
            >
              <i class="fa fa-paper-plane"></i>
              {{ currentIndex < questions.length - 1 ? '提交并下一题' : '完成面试' }}
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 正在生成报告 -->
    <div v-else-if="generatingReport" class="text-center py-24 glass-card animate-fade-in">
      <div class="inline-flex items-center justify-center w-24 h-24 rounded-3xl bg-gradient-to-br from-rose-300 to-sky-300 shadow-xl shadow-rose-300/20 mb-8 animate-pulse">
        <i class="fa fa-cogs text-white text-4xl"></i>
      </div>
      <h3 class="text-2xl font-bold text-slate-800 mb-4">AI 正在深度分析您的面试表现</h3>
      <p class="text-slate-500 text-lg mb-8">正在从技术深度、逻辑清晰度等多个维度综合评估...</p>
      <div class="max-w-md mx-auto p-1 bg-slate-100 rounded-full">
        <div class="h-3 bg-slate-200 rounded-full overflow-hidden">
          <div 
            class="h-full bg-gradient-to-r from-rose-300 to-sky-300 rounded-full transition-all duration-500 relative"
            :style="{ width: reportProgress + '%' }"
          >
            <div class="absolute top-0 right-0 bottom-0 left-0 bg-[linear-gradient(45deg,rgba(255,255,255,0.2)_25%,transparent_25%,transparent_50%,rgba(255,255,255,0.2)_50%,rgba(255,255,255,0.2)_75%,transparent_75%,transparent)] bg-[length:1rem_1rem] animate-[progress_1s_linear_infinite]"></div>
          </div>
        </div>
      </div>
      <p class="text-sm font-bold text-rose-600 mt-3">{{ reportProgress }}%</p>
    </div>

    <!-- 面试报告 -->
    <div v-else-if="interviewFinished && reportData" class="space-y-8 animate-fade-in">
      <!-- 报告头部 -->
      <div class="glass-card p-10 text-center relative overflow-hidden">
        <div class="absolute top-[-50%] left-[-10%] w-64 h-64 bg-emerald-500/10 rounded-full blur-3xl"></div>
        <div class="absolute bottom-[-50%] right-[-10%] w-64 h-64 bg-teal-500/10 rounded-full blur-3xl"></div>
        
        <div class="inline-flex items-center justify-center w-24 h-24 rounded-3xl bg-gradient-to-br from-emerald-500 to-teal-500 shadow-xl shadow-emerald-500/30 mb-6 relative z-10">
          <i class="fa fa-trophy text-white text-4xl"></i>
        </div>
        <h2 class="text-3xl font-extrabold text-slate-800 mb-3 relative z-10">面试已圆满完成！</h2>
        <p class="text-slate-500 text-lg relative z-10">您的全面评估报告已生成，请查阅详细反馈</p>
      </div>

      <!-- 总分 -->
      <div class="glass-card p-0 overflow-hidden flex flex-col md:flex-row">
        <div class="bg-gradient-to-br from-slate-800 to-slate-900 p-10 text-white md:w-1/3 flex flex-col justify-center items-center relative">
          <div class="absolute inset-0 bg-[url('data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjAiIGhlaWdodD0iMjAiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PGNpcmNsZSBjeD0iMiIgY3k9IjIiIHI9IjIiIGZpbGw9IiNmZmYiIGZpbGwtb3BhY2l0eT0iMC4wNSIvPjwvc3ZnPg==')]"></div>
          <p class="text-rose-200 font-medium mb-2 text-lg relative z-10">综合评估得分</p>
          <div class="text-7xl font-black text-transparent bg-clip-text bg-gradient-to-b from-white to-rose-200 relative z-10 drop-shadow-lg">
            {{ reportData.totalScore }}
          </div>
          <p class="text-sm text-slate-400 mt-4 relative z-10">满分 100 分</p>
        </div>
        <div class="p-10 md:w-2/3 flex flex-col justify-center">
          <h3 class="text-xl font-bold text-slate-800 mb-6 flex items-center">
            <i class="fa fa-bullseye text-rose-400 mr-3"></i>核心能力雷达图
          </h3>
          <div class="grid grid-cols-2 gap-6">
             <div class="bg-slate-50 rounded-xl p-4 border border-slate-100">
               <p class="text-sm text-slate-500 mb-1">完成题目</p>
               <p class="text-2xl font-bold text-slate-800">{{ questionCount }} <span class="text-sm font-normal text-slate-400">题</span></p>
             </div>
             <div class="bg-slate-50 rounded-xl p-4 border border-slate-100">
               <p class="text-sm text-slate-500 mb-1">表现评级</p>
               <p class="text-2xl font-bold" :class="getScoreColor(reportData.totalScore)">
                 {{ reportData.totalScore >= 80 ? '优秀 (A)' : reportData.totalScore >= 60 ? '良好 (B)' : '待提升 (C)' }}
               </p>
             </div>
          </div>
        </div>
      </div>

      <!-- 逐题分析 -->
      <div class="glass-card p-10">
        <h3 class="text-2xl font-bold text-slate-800 mb-8 flex items-center gap-3">
          <i class="fa fa-list-alt text-rose-400"></i>
          详细答题回顾与分析
        </h3>
        
        <div class="space-y-8">
          <div 
            v-for="(item, index) in reportData.answers" 
            :key="index"
            class="bg-white rounded-2xl p-8 shadow-[0_4px_20px_rgba(0,0,0,0.03)] border border-slate-100 transition-all hover:shadow-[0_8px_30px_rgba(0,0,0,0.06)]"
          >
            <div class="flex items-start justify-between mb-6">
              <div class="flex gap-4">
                <div class="w-12 h-12 shrink-0 rounded-xl bg-gradient-to-br from-slate-100 to-slate-200 flex items-center justify-center text-slate-600 font-black text-xl border border-slate-200">
                  {{ index + 1 }}
                </div>
                <div>
                  <p class="text-lg font-bold text-slate-800 leading-snug">{{ item.question }}</p>
                  <div class="mt-2">
                    <span class="tag-primary">{{ item.category }}</span>
                  </div>
                </div>
              </div>
              <div class="shrink-0 ml-4 text-center">
                <div class="text-3xl font-black" :class="getScoreColor(item.feedback.score)">{{ item.feedback.score }}</div>
                <div class="text-xs font-medium text-slate-400 mt-1">单题得分</div>
              </div>
            </div>
            
            <div class="bg-slate-50/80 rounded-xl p-5 mb-6 border border-slate-100">
              <p class="text-sm font-bold text-slate-700 mb-3 flex items-center"><i class="fa fa-user-circle-o mr-2 text-slate-400"></i>您的回答</p>
              <p class="text-slate-600 leading-relaxed">{{ item.answer }}</p>
            </div>
            
            <div class="grid grid-cols-1 md:grid-cols-2 gap-5 mb-6">
              <div 
                v-for="(dim, idx) in item.feedback.dimensions" 
                :key="idx"
                class="bg-white border border-slate-100 rounded-xl p-4 shadow-sm"
              >
                <div class="flex justify-between items-center mb-2">
                  <span class="text-sm font-bold text-slate-700">{{ dim.name }}</span>
                  <span class="text-sm font-black" :class="getScoreColor(dim.score)">{{ dim.score }} 分</span>
                </div>
                <div class="h-2 bg-slate-100 rounded-full overflow-hidden">
                  <div 
                    class="h-full rounded-full"
                    :class="getScoreBarColor(dim.score)"
                    :style="{ width: dim.score + '%' }"
                  ></div>
                </div>
              </div>
            </div>
            
            <div class="bg-rose-50/50 border border-rose-100 rounded-xl p-5">
              <p class="text-sm font-bold text-rose-700 mb-2 flex items-center"><i class="fa fa-lightbulb-o mr-2"></i>AI 专家点评</p>
              <p class="text-slate-700 leading-relaxed text-sm">{{ item.feedback.summary }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- 改进建议 -->
      <div class="glass-card p-10 border-t-4 border-t-amber-400">
        <h3 class="text-2xl font-bold text-slate-800 mb-8 flex items-center gap-3">
          <i class="fa fa-rocket text-amber-500"></i>
          行动与改进指南
        </h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-5">
          <div 
            v-for="(suggestion, index) in reportData.suggestions" 
            :key="index"
            class="flex items-start gap-4 bg-gradient-to-r from-amber-50 to-orange-50/30 border border-amber-100/50 rounded-xl p-5 transition-transform hover:-translate-y-1"
          >
            <div class="w-8 h-8 rounded-full bg-amber-100 flex items-center justify-center shrink-0">
              <i class="fa fa-star text-amber-500 text-sm"></i>
            </div>
            <span class="text-slate-700 font-medium leading-relaxed">{{ suggestion }}</span>
          </div>
        </div>
      </div>

      <!-- 重新面试按钮 -->
      <div class="flex justify-center gap-6 pt-4 pb-10">
        <button @click="restartInterview" class="gradient-btn px-10 py-4 text-lg">
          <i class="fa fa-refresh mr-2"></i>开启新一轮模拟面试
        </button>
      </div>
    </div>

    <div v-else class="glass-card p-10 text-center">
      <h3 class="text-2xl font-bold text-slate-800 mb-3">未能获取千问生成的问题</h3>
      <p class="text-slate-500 mb-6">请检查后端服务是否已启动，以及 `qwen.api.key` 是否有效。</p>
      <div class="flex justify-center gap-4">
        <button @click="loadQuestions" class="gradient-btn px-6 py-3">
          <i class="fa fa-refresh mr-2"></i>重试生成
        </button>
        <button @click="router.push('/')" class="px-6 py-3 rounded-xl border border-slate-200 text-slate-600 hover:bg-slate-50 transition-all">
          返回首页
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { generateQuestions, analyzeAnswer } from '@/services/ernieApi'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()

const loading = ref(true)
const questions = ref([])
const currentIndex = ref(0)
const answer = ref('')
const answers = ref([])
const isRecording = ref(false)
const recognition = ref(null)

const jdName = ref('')
const resumeName = ref('')
const questionCount = 10

const interviewFinished = ref(false)
const generatingReport = ref(false)
const reportProgress = ref(0)
const reportData = ref(null)

const currentQuestion = computed(() => questions.value[currentIndex.value] || {})

const buildStoredFeedbackText = (feedback) => {
  const dimensionLines = (feedback.dimensions || []).map(item => `${item.name}：${Math.round(item.score)}分${item.feedback ? `\n${item.feedback}` : ''}`)
  const suggestionLines = (feedback.suggestions || []).length
    ? [`建议：${feedback.suggestions.join('；')}`]
    : []

  return [
    ...dimensionLines,
    `综合得分：${Math.round(feedback.score || 0)}分`,
    feedback.summary || '',
    ...suggestionLines
  ].filter(Boolean).join('\n\n')
}

const buildOverallSummary = (results) => {
  const summaries = results
    .map(item => item.feedback.summary?.trim())
    .filter(Boolean)

  if (summaries.length > 0) {
    return summaries.slice(0, 3).join('；')
  }

  const suggestions = [...new Set(results.flatMap(item => item.feedback.suggestions || []).filter(Boolean))]
  return suggestions.length ? `综合改进方向：${suggestions.slice(0, 4).join('；')}` : ''
}

const persistLatestReport = (results, totalScore) => {
  const latestReport = {
    id: String(Date.now()),
    position: '',
    startTime: new Date().toISOString(),
    totalScore,
    overallSummary: buildOverallSummary(results),
    answers: results.map((item, index) => ({
      id: String(index + 1),
      question: {
        id: String(index + 1),
        questionText: item.question,
        category: item.category,
        difficulty: ''
      },
      answerText: item.answer,
      score: Math.round(item.feedback.score || 0),
      feedback: buildStoredFeedbackText(item.feedback),
      dimensions: item.feedback.dimensions || [],
      summary: item.feedback.summary || '',
      suggestions: item.feedback.suggestions || []
    }))
  }

  sessionStorage.setItem('latestInterviewReport', JSON.stringify(latestReport))
}

const getScoreColor = (score) => {
  if (score >= 80) return 'text-emerald-600'
  if (score >= 60) return 'text-amber-600'
  return 'text-red-500'
}

const getScoreBarColor = (score) => {
  if (score >= 80) return 'bg-gradient-to-r from-emerald-400 to-teal-500'
  if (score >= 60) return 'bg-gradient-to-r from-amber-400 to-orange-500'
  return 'bg-gradient-to-r from-red-400 to-red-500'
}

const loadInterviewInput = () => {
  const cachedInput = sessionStorage.getItem('interviewInput')
  if (cachedInput) {
    try {
      const parsed = JSON.parse(cachedInput)
      jdName.value = parsed?.jd || ''
      resumeName.value = parsed?.resume || ''
      return
    } catch (error) {
      console.error('读取面试输入失败:', error)
    }
  }

  jdName.value = String(route.query.jd || '')
  resumeName.value = String(route.query.resume || '')
}

const loadQuestions = async () => {
  loading.value = true
  const jdContent = String(jdName.value || '').trim()
  const resumeContent = String(resumeName.value || '').trim()

  if (!jdContent || !resumeContent) {
    questions.value = []
    loading.value = false
    ElMessage.error('未检测到可发送给大模型的 JD/简历内容，请返回首页重新上传')
    return
  }

  sessionStorage.removeItem('latestInterviewReport')

  try {
    console.log('开始生成问题，JD:', jdContent, '简历:', resumeContent)
    const result = await generateQuestions(
      jdContent,
      resumeContent,
      questionCount
    )

    if (result && result.length > 0) {
      questions.value = result.map((q, i) => ({ ...q, id: i + 1 }))
      ElMessage.success(`面试问题已生成，共${questions.value.length}道题`)
    } else {
      throw new Error('API返回空结果')
    }
  } catch (error) {
    console.error('生成问题失败:', error)
    questions.value = []
    const backendMsg = error?.response?.data?.message || error?.message || '请检查后端千问 API 配置与服务状态'
    ElMessage.error(`生成问题失败：${backendMsg}`)
  } finally {
    loading.value = false
  }
}

const submitAnswer = () => {
  if (!answer.value.trim()) {
    ElMessage.warning('请输入回答')
    return
  }
  
  answers.value[currentIndex.value] = {
    question: currentQuestion.value.question,
    category: currentQuestion.value.category,
    answer: answer.value
  }
  
  ElMessage.success(`第 ${currentIndex.value + 1} 题已提交`)
  
  if (currentIndex.value < questions.value.length - 1) {
    currentIndex.value++
    answer.value = ''
    window.scrollTo({ top: 0, behavior: 'smooth' })
  } else {
    generateReport()
  }
}

const generateReport = async () => {
  interviewFinished.value = true
  generatingReport.value = true
  reportProgress.value = 0
  
  ElMessage.info('开始深度分析您的答题...')
  
  const results = []
  
  for (let i = 0; i < answers.value.length; i++) {
    const item = answers.value[i]
    try {
      const feedback = await analyzeAnswer(item.question, item.answer)
      results.push({
        ...item,
        feedback: feedback
      })
    } catch (error) {
      console.error(`分析第 ${i + 1} 题失败:`, error)
      generatingReport.value = false
      interviewFinished.value = false
      const backendMsg = error?.response?.data?.message || error?.message || '请检查后端千问 API 配置与服务状态'
      ElMessage.error(`第 ${i + 1} 题分析失败：${backendMsg}`)
      return
    }
    
    reportProgress.value = Math.round(((i + 1) / answers.value.length) * 100)
  }
  
  const totalScore = Math.round(results.reduce((sum, r) => sum + r.feedback.score, 0) / results.length)
  
  const allSuggestions = []
  results.forEach(r => {
    if (r.feedback.suggestions) {
      allSuggestions.push(...r.feedback.suggestions)
    }
  })
  
  reportData.value = {
    totalScore,
    answers: results,
    suggestions: [...new Set(allSuggestions)].slice(0, 6)
  }

  persistLatestReport(results, totalScore)
  generatingReport.value = false
  ElMessage.success('面试报告已生成')
}

const restartInterview = () => {
  currentIndex.value = 0
  answer.value = ''
  answers.value = []
  interviewFinished.value = false
  generatingReport.value = false
  reportData.value = null
  loadQuestions()
}

const toggleSpeechRecognition = () => {
  if (isRecording.value) {
    stopRecording()
  } else {
    startRecording()
  }
}

const startRecording = () => {
  if (!('webkitSpeechRecognition' in window) && !('SpeechRecognition' in window)) {
    ElMessage.error('您的浏览器不支持语音识别功能，请使用 Chrome 浏览器')
    return
  }
  
  const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition
  recognition.value = new SpeechRecognition()
  recognition.value.lang = 'zh-CN'
  recognition.value.continuous = true
  recognition.value.interimResults = true
  
  recognition.value.onstart = () => {
    isRecording.value = true
    ElMessage.info('开始录音，请说话...')
  }
  
  recognition.value.onresult = (event) => {
    let finalTranscript = ''
    for (let i = event.resultIndex; i < event.results.length; i++) {
      const transcript = event.results[i][0].transcript
      if (event.results[i].isFinal) {
        finalTranscript += transcript
      }
    }
    
    if (finalTranscript) {
      if (answer.value) {
        answer.value += ' ' + finalTranscript
      } else {
        answer.value = finalTranscript
      }
    }
  }
  
  recognition.value.onerror = (event) => {
    console.error('语音识别错误:', event.error)
    if (event.error === 'no-speech') {
      ElMessage.warning('未检测到语音，请重新尝试')
    } else if (event.error === 'audio-capture') {
      ElMessage.error('未找到麦克风，请检查设备')
    } else {
      ElMessage.error('语音识别失败：' + event.error)
    }
    stopRecording()
  }
  
  recognition.value.onend = () => {
    isRecording.value = false
  }
  
  recognition.value.start()
}

const stopRecording = () => {
  if (recognition.value) {
    recognition.value.stop()
    recognition.value = null
    isRecording.value = false
    ElMessage.success('录音已停止')
  }
}

onMounted(() => {
  loadInterviewInput()
  loadQuestions()
})
</script>

<style scoped>
@keyframes progress {
  0% { background-position: 0 0; }
  100% { background-position: 1rem 0; }
}

.animate-fade-in {
  animation: fadeIn 0.5s ease-out forwards;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>
