<template>
  <div class="max-w-6xl mx-auto">
    <!-- Hero Section -->
    <div class="text-center mb-16 pt-10 relative">
      <div class="absolute top-0 left-1/2 -translate-x-1/2 w-3/4 h-64 bg-gradient-to-b from-rose-300/20 via-amber-200/10 to-transparent blur-3xl -z-10 rounded-full"></div>
      
      <div class="inline-flex items-center justify-center w-24 h-24 rounded-3xl bg-gradient-to-br from-rose-300 to-sky-300 shadow-[0_10px_30px_rgba(15,23,42,0.14)] mb-8 animate-float border border-white/30">
        <i class="fa fa-file-text text-white text-4xl"></i>
      </div>
      <h1 class="text-5xl md:text-6xl font-extrabold mb-6 tracking-tight">
        <span class="text-gradient">智能模拟面试</span>
        <span class="text-slate-800 ml-3">引擎</span>
      </h1>
      <p class="text-xl text-slate-500 max-w-2xl mx-auto font-medium leading-relaxed">
        只需上传 JD 和简历，AI 即刻为您生成专属面试问题。实时评分反馈，助您轻松拿下心仪 Offer。
      </p>
    </div>

    <!-- Feature Cards -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mb-16 px-4">
      <div class="feature-card group cursor-pointer relative overflow-hidden">
        <div class="absolute -right-10 -top-10 w-32 h-32 bg-rose-300/20 rounded-full blur-2xl group-hover:bg-rose-300/30 transition-all duration-500"></div>
        <div class="w-16 h-16 rounded-2xl bg-gradient-to-br from-rose-300 to-amber-200 flex items-center justify-center mb-6 group-hover:scale-110 group-hover:rotate-3 transition-transform duration-500 shadow-lg shadow-rose-300/20">
          <i class="fa fa-file-pdf-o text-white text-2xl"></i>
        </div>
        <h3 class="text-xl font-bold text-slate-800 mb-3">JD 深度分析</h3>
        <p class="text-slate-500 text-base leading-relaxed">基于先进的 NLP 模型，深度解析岗位描述，精准定位用人单位核心能力要求。</p>
      </div>
      
      <div class="feature-card group cursor-pointer relative overflow-hidden">
        <div class="absolute -right-10 -top-10 w-32 h-32 bg-sky-300/20 rounded-full blur-2xl group-hover:bg-sky-300/30 transition-all duration-500"></div>
        <div class="w-16 h-16 rounded-2xl bg-gradient-to-br from-sky-300 to-indigo-200 flex items-center justify-center mb-6 group-hover:scale-110 group-hover:rotate-3 transition-transform duration-500 shadow-lg shadow-sky-300/20">
          <i class="fa fa-user text-white text-2xl"></i>
        </div>
        <h3 class="text-xl font-bold text-slate-800 mb-3">简历精准匹配</h3>
        <p class="text-slate-500 text-base leading-relaxed">将您的个人履历与 JD 要求进行双向匹配比对，定制属于您的专属面试方案。</p>
      </div>
      
      <div class="feature-card group cursor-pointer relative overflow-hidden">
        <div class="absolute -right-10 -top-10 w-32 h-32 bg-teal-300/20 rounded-full blur-2xl group-hover:bg-teal-300/30 transition-all duration-500"></div>
        <div class="w-16 h-16 rounded-2xl bg-gradient-to-br from-teal-300 to-emerald-200 flex items-center justify-center mb-6 group-hover:scale-110 group-hover:rotate-3 transition-transform duration-500 shadow-lg shadow-teal-300/20">
          <i class="fa fa-lightbulb-o text-white text-2xl"></i>
        </div>
        <h3 class="text-xl font-bold text-slate-800 mb-3">AI 智能出题</h3>
        <p class="text-slate-500 text-base leading-relaxed">多维度智能生成问题，全面覆盖技术、行为、项目经验，还原真实面试场景。</p>
      </div>
    </div>

    <!-- Interview Settings Card -->
    <div class="glass-card p-10 mb-10 max-w-5xl mx-auto">
      <div class="flex items-center gap-4 mb-10">
        <div class="w-12 h-12 rounded-xl bg-gradient-to-br from-rose-300 to-sky-300 flex items-center justify-center shadow-md">
          <i class="fa fa-upload text-white text-lg"></i>
        </div>
        <div>
          <h2 class="text-2xl font-bold text-slate-800">准备面试材料</h2>
          <p class="text-slate-500 text-sm mt-1">请提供目标岗位要求和您的简历，以便我们为您量身定制</p>
        </div>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-10">
        <!-- JD Upload Section -->
        <div class="flex flex-col h-full">
          <div class="flex items-center justify-between mb-4">
            <label class="block text-base font-bold text-slate-700 flex items-center">
              <span class="w-8 h-8 rounded-full bg-sky-100 text-sky-600 flex items-center justify-center mr-2"><i class="fa fa-file-text"></i></span>
              目标岗位 JD
            </label>
            <div class="text-xs font-medium px-2 py-1 rounded bg-slate-100 text-slate-500 border border-slate-200">必填</div>
          </div>
          
          <!-- JD Input Tabs -->
          <div class="flex gap-2 mb-5 p-1 bg-slate-100/50 rounded-xl">
            <button 
              @click="jdInputType = 'text'"
              class="flex-1 px-4 py-2.5 rounded-lg font-medium transition-all duration-300 text-sm"
              :class="jdInputType === 'text' 
                ? 'bg-white text-sky-600 shadow-sm border border-slate-200/50' 
                : 'text-slate-500 hover:text-slate-700 hover:bg-white/50'"
            >
              <i class="fa fa-font mr-2"></i>文本
            </button>
            <button 
              @click="jdInputType = 'image'"
              class="flex-1 px-4 py-2.5 rounded-lg font-medium transition-all duration-300 text-sm"
              :class="jdInputType === 'image' 
                ? 'bg-white text-rose-600 shadow-sm border border-slate-200/50' 
                : 'text-slate-500 hover:text-slate-700 hover:bg-white/50'"
            >
              <i class="fa fa-image mr-2"></i>图片
            </button>
            <button 
              @click="jdInputType = 'file'"
              class="flex-1 px-4 py-2.5 rounded-lg font-medium transition-all duration-300 text-sm"
              :class="jdInputType === 'file' 
                ? 'bg-white text-rose-600 shadow-sm border border-slate-200/50' 
                : 'text-slate-500 hover:text-slate-700 hover:bg-white/50'"
            >
              <i class="fa fa-file mr-2"></i>文件
            </button>
          </div>

          <!-- JD Text Input -->
          <div v-if="jdInputType === 'text'" class="space-y-4 flex-grow flex flex-col">
            <textarea 
              v-model="jdText"
              placeholder="请在此输入或粘贴目标岗位的职责与要求..."
              class="w-full flex-grow min-h-[220px] px-5 py-4 rounded-2xl border-2 border-slate-200/80 bg-white/50 backdrop-blur
                     focus:border-rose-300 focus:ring-4 focus:ring-rose-200/40 focus:outline-none focus:bg-white
                     transition-all duration-300 text-slate-700 resize-none shadow-sm"
            ></textarea>
            
            <div class="flex items-center justify-between text-sm text-slate-400 px-1">
              <span>
                <i class="fa fa-text-width mr-1"></i>
                {{ jdText.length }}/5000
              </span>
              <button 
                v-if="jdText"
                @click="clearJDText"
                class="text-slate-400 hover:text-red-500 transition-colors font-medium flex items-center"
              >
                <i class="fa fa-trash-o mr-1"></i>清空
              </button>
            </div>
          </div>

          <!-- JD Image Upload -->
          <div v-else-if="jdInputType === 'image'" class="space-y-4 flex-grow flex flex-col">
            <div 
              @dragover.prevent="handleDragOver"
              @dragleave.prevent="handleDragLeave"
              @drop.prevent="handleDrop($event, 'jd')"
              @click="selectJDImage"
              class="border-2 border-dashed rounded-2xl p-8 text-center cursor-pointer transition-all duration-300 flex-grow flex flex-col justify-center min-h-[220px]"
              :class="jdImage ? 'border-rose-300 bg-rose-50/50' : 'border-slate-300 hover:border-rose-300 hover:bg-slate-50/80 bg-white/50'"
            >
              <div v-if="!jdImage" class="space-y-4">
                <div class="w-20 h-20 rounded-full bg-rose-50 flex items-center justify-center mx-auto transition-transform group-hover:scale-110">
                  <i class="fa fa-image text-rose-500 text-3xl"></i>
                </div>
                <div>
                  <p class="text-slate-700 font-medium text-lg">点击或拖拽上传 JD 图片</p>
                  <p class="text-sm text-slate-400 mt-1">支持 PNG, JPG 格式</p>
                </div>
              </div>
              <div v-else class="space-y-4 w-full">
                <div class="flex items-center justify-between bg-white rounded-xl p-4 shadow-sm border border-slate-100">
                  <div class="flex items-center gap-4">
                    <div class="w-12 h-12 rounded-lg bg-rose-50 flex items-center justify-center">
                      <i class="fa fa-image text-rose-500 text-xl"></i>
                    </div>
                    <div class="text-left">
                      <p class="font-medium text-slate-700 truncate max-w-[150px]">{{ jdImage.name }}</p>
                      <p class="text-xs text-slate-400 mt-0.5">{{ formatFileSize(jdImage.size) }}</p>
                    </div>
                  </div>
                  <button @click.stop="removeJDImage" class="w-8 h-8 rounded-full hover:bg-red-50 text-slate-400 hover:text-red-500 flex items-center justify-center transition-colors">
                    <i class="fa fa-times"></i>
                  </button>
                </div>
                <div class="rounded-xl overflow-hidden border border-slate-100 shadow-sm">
                  <img :src="jdImagePreview" class="w-full object-cover max-h-40" />
                </div>
              </div>
              <input type="file" ref="jdImageInput" @change="handleJDImageChange" accept="image/png,image/jpeg,image/jpg" class="hidden">
            </div>
          </div>

          <!-- JD File Upload -->
          <div v-else class="space-y-4 flex-grow flex flex-col">
            <div 
              @dragover.prevent="handleDragOver"
              @dragleave.prevent="handleDragLeave"
              @drop.prevent="handleDrop($event, 'jd')"
              @click="selectJDFile"
              class="border-2 border-dashed rounded-2xl p-8 text-center cursor-pointer transition-all duration-300 flex-grow flex flex-col justify-center min-h-[220px]"
              :class="jdFile ? 'border-rose-300 bg-rose-50/50' : 'border-slate-300 hover:border-rose-300 hover:bg-slate-50/80 bg-white/50'"
            >
              <div v-if="!jdFile" class="space-y-4">
                <div class="w-20 h-20 rounded-full bg-rose-50 flex items-center justify-center mx-auto transition-transform group-hover:scale-110">
                  <i class="fa fa-cloud-upload text-rose-500 text-3xl"></i>
                </div>
                <div>
                  <p class="text-slate-700 font-medium text-lg">点击或拖拽上传 JD 文件</p>
                  <p class="text-slate-700 font-medium text-lg">点击或拖拽上传简历文件</p>
                  <p class="text-sm text-slate-400 mt-1">支持可真实解析的 PDF、TXT（图片请切换到图片标签）</p>
                </div>
              </div>
              <div v-else class="space-y-4 w-full">
                <div class="flex items-center justify-between bg-white rounded-xl p-5 shadow-sm border border-slate-100">
                  <div class="flex items-center gap-4">
                    <div class="w-12 h-12 rounded-lg bg-red-50 flex items-center justify-center">
                      <i class="fa fa-file-pdf-o text-red-500 text-xl"></i>
                    </div>
                    <div class="text-left">
                      <p class="font-medium text-slate-700 truncate max-w-[150px]">{{ jdFile.name }}</p>
                      <p class="text-xs text-slate-400 mt-0.5">已解析成功 · {{ formatFileSize(jdFile.size) }}</p>
                    </div>
                  </div>
                  <button @click.stop="removeJDFile" class="w-8 h-8 rounded-full hover:bg-red-50 text-slate-400 hover:text-red-500 flex items-center justify-center transition-colors">
                    <i class="fa fa-times"></i>
                  </button>
                </div>
              </div>
              <input type="file" ref="jdInput" @change="handleJDFileChange" accept=".pdf,.txt" class="hidden">
            </div>
          </div>
        </div>

        <!-- Resume Upload Section -->
        <div class="flex flex-col h-full">
          <div class="flex items-center justify-between mb-4">
            <label class="block text-base font-bold text-slate-700 flex items-center">
              <span class="w-8 h-8 rounded-full bg-emerald-100 text-emerald-600 flex items-center justify-center mr-2"><i class="fa fa-user"></i></span>
              您的个人简历
            </label>
            <div class="text-xs font-medium px-2 py-1 rounded bg-slate-100 text-slate-500 border border-slate-200">必填</div>
          </div>
            
          <!-- Resume Input Tabs -->
          <div class="flex gap-2 mb-5 p-1 bg-slate-100/50 rounded-xl">
            <button 
              @click="resumeInputType = 'text'"
              class="flex-1 px-4 py-2.5 rounded-lg font-medium transition-all duration-300 text-sm"
              :class="resumeInputType === 'text' 
                ? 'bg-white text-emerald-600 shadow-sm border border-slate-200/50' 
                : 'text-slate-500 hover:text-slate-700 hover:bg-white/50'"
            >
              <i class="fa fa-font mr-2"></i>文本
            </button>
            <button 
              @click="resumeInputType = 'image'"
              class="flex-1 px-4 py-2.5 rounded-lg font-medium transition-all duration-300 text-sm"
              :class="resumeInputType === 'image' 
                ? 'bg-white text-emerald-600 shadow-sm border border-slate-200/50' 
                : 'text-slate-500 hover:text-slate-700 hover:bg-white/50'"
            >
              <i class="fa fa-image mr-2"></i>图片
            </button>
            <button 
              @click="resumeInputType = 'file'"
              class="flex-1 px-4 py-2.5 rounded-lg font-medium transition-all duration-300 text-sm"
              :class="resumeInputType === 'file' 
                ? 'bg-white text-emerald-600 shadow-sm border border-slate-200/50' 
                : 'text-slate-500 hover:text-slate-700 hover:bg-white/50'"
            >
              <i class="fa fa-file mr-2"></i>文件
            </button>
          </div>

          <!-- Resume Text Input -->
          <div v-if="resumeInputType === 'text'" class="space-y-4 flex-grow flex flex-col">
            <textarea 
              v-model="resumeText"
              placeholder="请在此输入或粘贴您的个人简历内容..."
              class="w-full flex-grow min-h-[220px] px-5 py-4 rounded-2xl border-2 border-slate-200/80 bg-white/50 backdrop-blur
                     focus:border-emerald-500 focus:ring-4 focus:ring-emerald-500/10 focus:outline-none focus:bg-white
                     transition-all duration-300 text-slate-700 resize-none shadow-sm"
            ></textarea>
            
            <div class="flex items-center justify-between text-sm text-slate-400 px-1">
              <span>
                <i class="fa fa-text-width mr-1"></i>
                {{ resumeText.length }}/5000
              </span>
              <button 
                v-if="resumeText"
                @click="clearResumeText"
                class="text-slate-400 hover:text-red-500 transition-colors font-medium flex items-center"
              >
                <i class="fa fa-trash-o mr-1"></i>清空
              </button>
            </div>
          </div>

          <!-- Resume Image Upload -->
          <div v-else-if="resumeInputType === 'image'" class="space-y-4 flex-grow flex flex-col">
            <div 
              @dragover.prevent="handleDragOver"
              @dragleave.prevent="handleDragLeave"
              @drop.prevent="handleDrop($event, 'resume')"
              @click="selectResumeImage"
              class="border-2 border-dashed rounded-2xl p-8 text-center cursor-pointer transition-all duration-300 flex-grow flex flex-col justify-center min-h-[220px]"
              :class="resumeImage ? 'border-emerald-400 bg-emerald-50/50' : 'border-slate-300 hover:border-emerald-400 hover:bg-slate-50/80 bg-white/50'"
            >
              <div v-if="!resumeImage" class="space-y-4">
                <div class="w-20 h-20 rounded-full bg-emerald-50 flex items-center justify-center mx-auto transition-transform group-hover:scale-110">
                  <i class="fa fa-image text-emerald-500 text-3xl"></i>
                </div>
                <div>
                  <p class="text-slate-700 font-medium text-lg">点击或拖拽上传简历图片</p>
                  <p class="text-sm text-slate-400 mt-1">支持 PNG, JPG 格式</p>
                </div>
              </div>
              <div v-else class="space-y-4 w-full">
                <div class="flex items-center justify-between bg-white rounded-xl p-4 shadow-sm border border-slate-100">
                  <div class="flex items-center gap-4">
                    <div class="w-12 h-12 rounded-lg bg-emerald-50 flex items-center justify-center">
                      <i class="fa fa-image text-emerald-500 text-xl"></i>
                    </div>
                    <div class="text-left">
                      <p class="font-medium text-slate-700 truncate max-w-[150px]">{{ resumeImage.name }}</p>
                      <p class="text-xs text-slate-400 mt-0.5">{{ formatFileSize(resumeImage.size) }}</p>
                    </div>
                  </div>
                  <button @click.stop="removeResumeImage" class="w-8 h-8 rounded-full hover:bg-red-50 text-slate-400 hover:text-red-500 flex items-center justify-center transition-colors">
                    <i class="fa fa-times"></i>
                  </button>
                </div>
                <div class="rounded-xl overflow-hidden border border-slate-100 shadow-sm">
                  <img :src="resumeImagePreview" class="w-full object-cover max-h-40" />
                </div>
              </div>
              <input type="file" ref="resumeImageInput" @change="handleResumeImageChange" accept="image/png,image/jpeg,image/jpg" class="hidden">
            </div>
          </div>

          <!-- Resume File Upload -->
          <div v-else class="space-y-4 flex-grow flex flex-col">
            <div 
              @dragover.prevent="handleDragOver"
              @dragleave.prevent="handleDragLeave"
              @drop.prevent="handleDrop($event, 'resume')"
              @click="selectResumeFile"
              class="border-2 border-dashed rounded-2xl p-8 text-center cursor-pointer transition-all duration-300 flex-grow flex flex-col justify-center min-h-[220px]"
              :class="resumeFile ? 'border-emerald-400 bg-emerald-50/50' : 'border-slate-300 hover:border-emerald-400 hover:bg-slate-50/80 bg-white/50'"
            >
              <div v-if="!resumeFile" class="space-y-4">
                <div class="w-20 h-20 rounded-full bg-emerald-50 flex items-center justify-center mx-auto transition-transform group-hover:scale-110">
                  <i class="fa fa-id-card text-emerald-500 text-3xl"></i>
                </div>
                <div>
                  <p class="text-slate-700 font-medium text-lg">点击或拖拽上传简历文件</p>
                  <p class="text-sm text-slate-400 mt-1">仅支持可真实解析的 PDF、TXT</p>
                </div>
              </div>
              <div v-else class="space-y-4 w-full">
                <div class="flex items-center justify-between bg-white rounded-xl p-5 shadow-sm border border-slate-100">
                  <div class="flex items-center gap-4">
                    <div class="w-12 h-12 rounded-lg bg-emerald-50 flex items-center justify-center">
                      <i class="fa fa-file-text-o text-emerald-500 text-xl"></i>
                    </div>
                    <div class="text-left">
                      <p class="font-medium text-slate-700 truncate max-w-[150px]">{{ resumeFile.name }}</p>
                      <p class="text-xs text-slate-400 mt-0.5">已解析成功 · {{ formatFileSize(resumeFile.size) }}</p>
                    </div>
                  </div>
                  <button @click.stop="removeResumeFile" class="w-8 h-8 rounded-full hover:bg-red-50 text-slate-400 hover:text-red-500 flex items-center justify-center transition-colors">
                    <i class="fa fa-times"></i>
                  </button>
                </div>
              </div>
              <input type="file" ref="resumeInput" @change="handleResumeFileChange" accept=".pdf,.txt" class="hidden">
            </div>
          </div>
        </div>
      </div>

      <!-- Start Button -->
      <div class="mt-12 text-center">
        <button 
          @click="startInterview" 
          :disabled="!hasValidInput() || isParsing"
          class="w-full md:w-2/3 mx-auto gradient-btn text-lg py-4 flex items-center justify-center gap-3 disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none disabled:hover:shadow-lg rounded-2xl"
        >
          <i v-if="isParsing" class="fa fa-spinner fa-spin"></i>
          <i v-else class="fa fa-play-circle text-xl"></i>
          {{ isParsing ? '正在解析文件...' : '开始定制面试' }}
          <i v-if="!isParsing" class="fa fa-arrow-right ml-1"></i>
        </button>
        <p class="text-slate-400 text-sm mt-5 flex items-center justify-center">
          <i class="fa fa-shield mr-2"></i>
          我们严格保护您的隐私，所有上传资料仅用于本次面试生成
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import * as pdfjsLib from 'pdfjs-dist'
import pdfWorker from 'pdfjs-dist/build/pdf.worker.min.mjs?url'

pdfjsLib.GlobalWorkerOptions.workerSrc = pdfWorker

const router = useRouter()

const jdInputType = ref('text')
const resumeInputType = ref('text')
const jdText = ref('')
const resumeText = ref('')
const jdFile = ref(null)
const resumeFile = ref(null)
const jdImage = ref(null)
const resumeImage = ref(null)
const jdImagePreview = ref('')
const resumeImagePreview = ref('')
const jdInput = ref(null)
const resumeInput = ref(null)
const jdImageInput = ref(null)
const resumeImageInput = ref(null)
const jdFileContent = ref('')
const resumeFileContent = ref('')
const jdImageContent = ref('')
const resumeImageContent = ref('')
const isParsing = ref(false)

const formatFileSize = (bytes) => {
  if (bytes === 0) return '0 B'
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return (bytes / Math.pow(k, i)).toFixed(2) + ' ' + sizes[i]
}

const extractPDFText = async (file) => {
  try {
    const arrayBuffer = await file.arrayBuffer()
    const pdf = await pdfjsLib.getDocument({ data: arrayBuffer }).promise
    let fullText = ''
    
    for (let i = 1; i <= pdf.numPages; i++) {
      const page = await pdf.getPage(i)
      const textContent = await page.getTextContent()
      const pageText = textContent.items.map(item => item.str).join(' ')
      fullText += pageText + '\n'
    }
    
    return fullText.trim()
  } catch (error) {
    console.error('PDF解析失败:', error)
    throw new Error('PDF解析失败')
  }
}

const extractTextFile = async (file) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.onload = (e) => resolve(e.target.result)
    reader.onerror = reject
    reader.readAsText(file)
  })
}

const readImageAsDataUrl = async (file) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.onload = (e) => resolve(e.target.result)
    reader.onerror = reject
    reader.readAsDataURL(file)
  })
}

const selectJDFile = () => jdInput.value?.click()
const selectJDImage = () => jdImageInput.value?.click()
const selectResumeFile = () => resumeInput.value?.click()
const selectResumeImage = () => resumeImageInput.value?.click()

const handleJDFileChange = async (event) => {
  const file = event.target.files[0]
  if (!file) return

  const isPdf = file.type === 'application/pdf'
  const isTxt = file.name.toLowerCase().endsWith('.txt')

  if (!isPdf && !isTxt) {
    ElMessage.error('JD 仅支持可真实解析的 PDF 或 TXT，Word 和图片不会发送给大模型')
    removeJDFile()
    return
  }

  jdFile.value = file
  isParsing.value = true

  try {
    jdFileContent.value = isPdf ? await extractPDFText(file) : await extractTextFile(file)
    if (!jdFileContent.value.trim()) {
      throw new Error('JD 文件内容为空')
    }
    ElMessage.success('上传jd成功')
  } catch (error) {
    removeJDFile()
    ElMessage.error('JD 文件解析失败，请改用文本粘贴或重新上传 PDF/TXT')
  } finally {
    isParsing.value = false
  }
}

const handleJDImageChange = async (event) => {
  const file = event?.target?.files?.[0]
  if (!file) return

  if (!file.type.startsWith('image/')) {
    ElMessage.error('JD 图片格式不正确，请上传 PNG/JPG')
    removeJDImage()
    return
  }

  isParsing.value = true
  try {
    jdImage.value = file
    const dataUrl = await readImageAsDataUrl(file)
    jdImagePreview.value = dataUrl
    jdImageContent.value = dataUrl
    ElMessage.success('上传jd成功')
  } catch (error) {
    removeJDImage()
    ElMessage.error('JD 图片读取失败，请重新上传')
  } finally {
    isParsing.value = false
  }
}

const handleResumeFileChange = async (event) => {
  const file = event.target.files[0]
  if (!file) return

  const isPdf = file.type === 'application/pdf'
  const isTxt = file.name.toLowerCase().endsWith('.txt')

  if (!isPdf && !isTxt) {
    ElMessage.error('简历仅支持可真实解析的 PDF 或 TXT，Word 和图片不会发送给大模型')
    removeResumeFile()
    return
  }

  resumeFile.value = file
  isParsing.value = true

  try {
    resumeFileContent.value = isPdf ? await extractPDFText(file) : await extractTextFile(file)
    if (!resumeFileContent.value.trim()) {
      throw new Error('简历文件内容为空')
    }
    ElMessage.success('简历文件已解析成功')
  } catch (error) {
    removeResumeFile()
    ElMessage.error('简历文件解析失败，请改用文本粘贴或重新上传 PDF/TXT')
  } finally {
    isParsing.value = false
  }
}

const handleResumeImageChange = async (event) => {
  const file = event?.target?.files?.[0]
  if (!file) return

  if (!file.type.startsWith('image/')) {
    ElMessage.error('简历图片格式不正确，请上传 PNG/JPG')
    removeResumeImage()
    return
  }

  isParsing.value = true
  try {
    resumeImage.value = file
    const dataUrl = await readImageAsDataUrl(file)
    resumeImagePreview.value = dataUrl
    resumeImageContent.value = dataUrl
    ElMessage.success('上传简历成功')
  } catch (error) {
    removeResumeImage()
    ElMessage.error('简历图片读取失败，请重新上传')
  } finally {
    isParsing.value = false
  }
}

const removeJDFile = () => {
  jdFile.value = null
  jdFileContent.value = ''
  jdInput.value.value = ''
}

const removeJDImage = () => {
  jdImage.value = null
  jdImagePreview.value = ''
  jdImageContent.value = ''
  jdImageInput.value.value = ''
}

const removeResumeFile = () => {
  resumeFile.value = null
  resumeFileContent.value = ''
  resumeInput.value.value = ''
}

const removeResumeImage = () => {
  resumeImage.value = null
  resumeImagePreview.value = ''
  resumeImageContent.value = ''
  resumeImageInput.value.value = ''
}

const clearJDText = () => jdText.value = ''
const clearResumeText = () => resumeText.value = ''

const handleDragOver = (event) => {
  event.dataTransfer.dropEffect = 'copy'
}

const handleDragLeave = () => {}

const handleDrop = (event, type) => {
  event.dataTransfer.dropEffect = 'copy'
  const file = event.dataTransfer.files[0]
  if (file) {
    if (type === 'jd') {
      const isImage = file.type.startsWith('image/')
      if(isImage) {
        jdInputType.value = 'image'
        handleJDImageChange({ target: { files: [file] } })
      } else {
        jdInputType.value = 'file'
        handleJDFileChange({ target: { files: [file] } })
      }
    } else if (type === 'resume') {
      const isImage = file.type.startsWith('image/')
      if(isImage) {
        resumeInputType.value = 'image'
        handleResumeImageChange({ target: { files: [file] } })
      } else {
        resumeInputType.value = 'file'
        handleResumeFileChange({ target: { files: [file] } })
      }
    }
  }
}

const getJDContent = () => {
  if (jdInputType.value === 'text') return jdText.value.trim()
  if (jdInputType.value === 'file') return jdFileContent.value.trim()
  if (jdInputType.value === 'image') return jdImageContent.value.trim()
  return ''
}

const getResumeContent = () => {
  if (resumeInputType.value === 'text') return resumeText.value.trim()
  if (resumeInputType.value === 'file') return resumeFileContent.value.trim()
  if (resumeInputType.value === 'image') return resumeImageContent.value.trim()
  return ''
}

const hasValidInput = () => Boolean(getJDContent() && getResumeContent())

const startInterview = () => {
  if (isParsing.value) {
    ElMessage.warning('文件正在解析中，请稍候...')
    return
  }

  const jdContent = getJDContent()
  const resumeContent = getResumeContent()

  if (!jdContent) {
    ElMessage.warning('请提供可被真实解析的 JD 正文，支持文本、PDF/TXT 或图片')
    return
  }

  if (!resumeContent) {
    ElMessage.warning('请提供可被真实解析的简历正文，支持文本、PDF/TXT 或图片')
    return
  }

  sessionStorage.removeItem('latestInterviewReport')
  sessionStorage.setItem('interviewInput', JSON.stringify({
    jd: jdContent,
    resume: resumeContent
  }))

  router.push({ path: '/interview' })
}
</script>

<style scoped>
</style>
