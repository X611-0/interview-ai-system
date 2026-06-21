<!-- src/views/ReportView.vue -->
<template>
  <div class="max-w-6xl mx-auto py-6">
    <div v-if="loading" class="text-center py-24 glass-card">
      <div class="inline-flex items-center justify-center w-20 h-20 rounded-2xl bg-gradient-to-br from-rose-300 to-sky-300 shadow-xl shadow-rose-300/20 mb-6 animate-pulse">
        <i class="fa fa-spinner fa-spin text-white text-3xl"></i>
      </div>
      <h3 class="text-2xl font-bold text-slate-800 mb-2">正在加载本轮真实报告...</h3>
    </div>

    <div v-else>
      <!-- 报告头部 -->
      <div class="glass-card p-0 mb-8 overflow-hidden relative">
        <div class="absolute top-0 right-0 w-64 h-64 bg-rose-300/20 rounded-full blur-3xl -translate-y-1/2 translate-x-1/2 pointer-events-none"></div>
        <div class="p-8 md:p-12 flex flex-col md:flex-row justify-between items-center gap-8 relative z-10">
          <div>
            <div class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-rose-50 text-rose-700 text-sm font-medium mb-4">
              <i class="fa fa-calendar"></i>
              {{ formatDate(interview.startTime) }}
            </div>
            <h1 class="text-3xl font-extrabold text-slate-800 mb-2">{{ interview.position || '本轮面试' }} 评估报告</h1>
            <p class="text-slate-500 text-lg">基于您本次面试表现生成的综合反馈</p>
          </div>
          <div class="flex items-center justify-center shrink-0 w-36 h-36 rounded-full border-8 border-slate-50 shadow-inner relative bg-gradient-to-br from-rose-50 to-sky-50">
            <svg class="absolute inset-0 w-full h-full -rotate-90" viewBox="0 0 100 100">
              <circle cx="50" cy="50" r="46" fill="none" stroke="#e2e8f0" stroke-width="8" />
              <circle cx="50" cy="50" r="46" fill="none" stroke="url(#scoreGradient)" stroke-width="8" stroke-linecap="round" :stroke-dasharray="2 * Math.PI * 46" :stroke-dashoffset="2 * Math.PI * 46 * (1 - interview.totalScore / 100)" class="transition-all duration-1000 ease-out" />
              <defs>
                <linearGradient id="scoreGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" stop-color="#e2b6b6" />
                  <stop offset="100%" stop-color="#a9c4dd" />
                </linearGradient>
              </defs>
            </svg>
            <div class="text-center relative z-10">
              <p class="text-4xl font-black text-transparent bg-clip-text bg-gradient-to-br from-rose-400 to-sky-500">{{ interview.totalScore }}</p>
              <p class="text-xs text-slate-400 font-bold mt-1">综合评分</p>
            </div>
          </div>
        </div>
      </div>

      
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8 mb-8">
        <!-- 综合评价 (Left Col) -->
        <div class="lg:col-span-2 space-y-8">
          <div class="glass-card p-8 h-full">
            <h3 class="text-xl font-bold text-slate-800 mb-6 flex items-center">
              <i class="fa fa-star text-amber-400 mr-3 text-2xl"></i>综合专家评价
            </h3>
            <div class="p-6 bg-gradient-to-br from-slate-50 to-rose-50/30 rounded-2xl border border-slate-100 shadow-sm">
              <p class="text-slate-700 text-lg leading-relaxed font-medium">
                {{ generateSummary() }}
              </p>
            </div>
          </div>
        </div>

        <!-- 评分雷达图 (Right Col) -->
        <div class="glass-card p-8 flex flex-col items-center justify-center">
          <h3 class="text-xl font-bold text-slate-800 mb-6 self-start flex items-center">
            <i class="fa fa-bullseye text-rose-400 mr-3 text-2xl"></i>能力维度分析
          </h3>
          <div class="w-full h-64 relative">
            <div class="radar-chart w-full h-full" ref="radarChartRef"></div>
          </div>
        </div>
      </div>
      
      <!-- 问题与回答详情 -->
      <div class="glass-card p-8">
        <h3 class="text-2xl font-bold text-slate-800 mb-8 flex items-center">
          <i class="fa fa-history text-rose-400 mr-3"></i>答题详情回顾
        </h3>
        
        <div class="space-y-8">
          <div 
            class="bg-white rounded-2xl shadow-[0_4px_20px_rgba(0,0,0,0.03)] border border-slate-100 p-8 transition-all duration-300 hover:shadow-[0_8px_30px_rgba(0,0,0,0.06)]"
            v-for="(answer, index) in interview.answers" 
            :key="answer.id || index"
          >
            <div class="flex justify-between items-start mb-6">
              <div class="flex items-start gap-4">
                <div class="w-10 h-10 shrink-0 rounded-xl bg-gradient-to-br from-rose-300 to-sky-300 text-white flex items-center justify-center font-black shadow-md mt-1">
                  {{ index + 1 }}
                </div>
                <div>
                  <h4 class="text-lg font-bold text-slate-800 leading-snug">{{ answer.question.questionText }}</h4>
                </div>
              </div>
              <div class="shrink-0 ml-4 px-4 py-1.5 rounded-full text-sm font-bold border" 
                   :class="getScoreClass(answer.score)">
                {{ answer.score }} 分
              </div>
            </div>
            
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
              <div class="bg-slate-50 rounded-xl p-5 border border-slate-100">
                <h4 class="text-slate-500 font-bold mb-3 text-sm flex items-center"><i class="fa fa-user-circle-o mr-2"></i>您的回答</h4>
                <div class="text-slate-700 whitespace-pre-wrap leading-relaxed text-sm">
                  {{ answer.answerText }}
                </div>
              </div>
              
              <div class="bg-rose-50/50 rounded-xl p-5 border border-rose-100 relative">
                <i class="fa fa-quote-right absolute top-4 right-4 text-3xl text-rose-400/10"></i>
                <h4 class="text-rose-700 font-bold mb-3 text-sm flex items-center"><i class="fa fa-lightbulb-o mr-2"></i>AI 深度反馈</h4>
                <div class="text-slate-700 text-sm leading-relaxed" v-html="parseFeedback(answer.feedback)"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 操作按钮 -->
      <div class="flex justify-center gap-6 mt-10">
        <button @click="downloadPDF" class="gradient-btn-secondary px-8 py-3.5 flex items-center gap-2">
          <i class="fa fa-download"></i> 下载 PDF 报告
        </button>
        <button @click="startNewInterview" class="gradient-btn px-8 py-3.5 flex items-center gap-2">
          <i class="fa fa-refresh"></i> 开启新面试
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

interface Question {
  id: string;
  questionText: string;
  category: string;
  difficulty: string;
}

interface Dimension {
  name: string;
  score: number;
  feedback: string;
}

interface Answer {
  id?: string;
  question: Question;
  answerText: string;
  score: number;
  feedback: string;
  dimensions?: Dimension[];
  summary?: string;
  suggestions?: string[];
}


interface InterviewReport {
  id: string;
  position: string;
  startTime: Date;
  totalScore: number;
  overallSummary?: string;
  answers: Answer[];
}

const router = useRouter();
const radarChartRef = ref<HTMLDivElement | null>(null);
const loading = ref(true);

const interview = reactive<InterviewReport>({
  id: '',
  position: '',
  startTime: new Date(),
  totalScore: 0,
  overallSummary: '',
  answers: []
});

onMounted(async () => {
  try {
    const cachedReport = sessionStorage.getItem('latestInterviewReport');
    if (!cachedReport) {
      throw new Error('暂无真实面试报告');
    }

    const report = JSON.parse(cachedReport);
    interview.id = report.id;
    interview.position = report.position || '';
    interview.startTime = new Date(report.startTime);
    interview.totalScore = report.totalScore;
    interview.overallSummary = report.overallSummary || '';
    interview.answers = report.answers || [];

    setTimeout(() => {
      initRadarChart();
    }, 300);
  } catch (error) {
    console.error('获取报告失败:', error);
    ElMessage.error('暂无真实面试报告，请先完成一次面试');
    router.push('/');
  } finally {
    loading.value = false;
  }
});

const formatDate = (date: Date) => {
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  });
};

const getScoreClass = (score: number) => {
  if (score >= 80) return 'bg-emerald-50 text-emerald-600 border-emerald-200';
  if (score >= 60) return 'bg-amber-50 text-amber-600 border-amber-200';
  return 'bg-red-50 text-red-600 border-red-200';
};

const parseFeedback = (feedback: string) => {
  return feedback
    .replace(/(技术正确性|逻辑清晰度|表达流畅度|完整性|综合得分)：(\d+)分/g, '<strong class="text-rose-700 block mt-2 mb-1">$1：$2分</strong>')
    .replace(/\n/g, '<br>');
};

const generateSummary = () => {
  if (interview.overallSummary?.trim()) {
    return interview.overallSummary.trim();
  }

  const summaries = interview.answers
    .map(answer => answer.summary?.trim())
    .filter((item): item is string => Boolean(item));

  if (summaries.length > 0) {
    return summaries.slice(0, 3).join('；');
  }

  const suggestions = [...new Set(interview.answers.flatMap(answer => answer.suggestions || []).filter(Boolean))];
  if (suggestions.length > 0) {
    return `综合改进方向：${suggestions.slice(0, 4).join('；')}`;
  }

  return '当前报告仅展示千问返回的逐题反馈；如需综合总结，请先完成完整面试。';
};

const downloadPDF = () => {
  import('html2pdf.js').then(module => {
    const html2pdf = module.default || module;
    const element = document.querySelector('.max-w-6xl');
    if (element) {
      const opt = {
        margin:       10,
        filename:     `面试评估报告_${new Date().getTime()}.pdf`,
        image:        { type: 'jpeg', quality: 0.98 },
        html2canvas:  { scale: 2, useCORS: true },
        jsPDF:        { unit: 'mm', format: 'a4', orientation: 'portrait' }
      };
      html2pdf().set(opt).from(element).save();
    }
  }).catch(error => {
    console.error('下载PDF失败:', error);
    ElMessage.error('下载失败，请稍后重试');
  });
};

const startNewInterview = () => {
  sessionStorage.removeItem('latestInterviewReport');
  router.push('/');
};

const initRadarChart = () => {
  const ChartLib = (window as any).Chart;
  if (!radarChartRef.value || !ChartLib || interview.answers.length === 0) return;
  
  const categories = ['技术正确性', '逻辑清晰度', '表达流畅度', '完整性'];
  const scores = categories.map(category => {
    const matchedDimensions = interview.answers
      .flatMap(answer => answer.dimensions || [])
      .filter(dimension => dimension.name === category);

    if (matchedDimensions.length === 0) {
      return 0;
    }

    const total = matchedDimensions.reduce((sum, dimension) => sum + dimension.score, 0);
    return Math.round(total / matchedDimensions.length);
  });
  
  new ChartLib(radarChartRef.value, {


    type: 'radar',
    data: {
      labels: categories,
      datasets: [{
        label: '能力评估',
        data: scores,
        backgroundColor: 'rgba(154, 180, 205, 0.22)',
        borderColor: 'rgba(154, 180, 205, 0.9)',
        borderWidth: 2,
        pointBackgroundColor: '#ffffff',
        pointBorderColor: 'rgba(154, 180, 205, 0.9)',
        pointBorderWidth: 2,
        pointRadius: 4,
        pointHoverRadius: 6
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        r: {
          beginAtZero: true,
          max: 100,
          ticks: {
            stepSize: 20,
            display: false
          },
          grid: {
            color: 'rgba(0, 0, 0, 0.05)'
          },
          angleLines: {
            color: 'rgba(0, 0, 0, 0.05)'
          },
          pointLabels: {
            font: {
              family: 'Inter',
              size: 13,
              weight: '600'
            },
            color: '#475569'
          }
        }
      },
      plugins: {
        legend: {
          display: false
        },
        tooltip: {
          backgroundColor: 'rgba(15, 23, 42, 0.9)',
          titleFont: { family: 'Inter', size: 13 },
          bodyFont: { family: 'Inter', size: 14, weight: 'bold' },
          padding: 12,
          cornerRadius: 8,
          displayColors: false
        }
      }
    }
  });
};
</script>

<style scoped>
.radar-chart {
  min-height: 300px;
}
</style>