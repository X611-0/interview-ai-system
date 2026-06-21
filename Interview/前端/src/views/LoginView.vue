<template>
  <div class="max-w-4xl mx-auto mt-12 mb-16 flex rounded-3xl overflow-hidden shadow-[0_20px_50px_rgba(0,0,0,0.08)] bg-white min-h-[550px]">
    <!-- Left Decorative Side -->
    <div class="hidden md:flex flex-col w-5/12 bg-gradient-to-br from-rose-300 via-amber-200 to-sky-300 p-12 text-white justify-center relative overflow-hidden">
      <!-- Decorative background elements -->
      <div class="absolute top-[-10%] right-[-10%] w-64 h-64 rounded-full bg-white/12 blur-3xl"></div>
      <div class="absolute bottom-[-10%] left-[-10%] w-64 h-64 rounded-full bg-white/12 blur-3xl"></div>
      
      <div class="relative z-10">
        <div class="w-16 h-16 rounded-2xl bg-white/25 backdrop-blur-lg flex items-center justify-center mb-8 shadow-xl">
          <i class="fa fa-comments text-white text-3xl"></i>
        </div>
        <h2 class="text-3xl font-bold mb-4">{{ mode === 'login' ? '欢迎回来' : '开启新旅程' }}</h2>
        <p class="text-white/85 text-base leading-relaxed mb-8">
          智能模拟面试平台，通过AI技术全方位评估您的面试表现，助您轻松拿下心仪 Offer。
        </p>
        <div class="space-y-4 text-sm text-white/80">
          <div class="flex items-center gap-3"><i class="fa fa-check-circle"></i> 智能生成专属问题</div>
          <div class="flex items-center gap-3"><i class="fa fa-check-circle"></i> 实时语音录入评分</div>
          <div class="flex items-center gap-3"><i class="fa fa-check-circle"></i> 全维度面试报告</div>
        </div>
      </div>
    </div>


    <!-- Right Form Side -->
    <div class="w-full md:w-7/12 p-10 md:p-14 flex flex-col justify-center relative bg-white/60 backdrop-blur-xl">
      <div class="mb-8">
        <h2 class="text-2xl font-bold text-slate-800 mb-2">账号{{ mode === 'login' ? '登录' : '注册' }}</h2>
        <p class="text-slate-500 text-sm">
          {{ mode === 'login' ? '请输入您的账号信息以继续' : '请填写以下信息完成注册' }}
        </p>
      </div>

      <el-tabs v-model="mode" class="mb-6 custom-tabs">
        <el-tab-pane label="登录" name="login" />
        <el-tab-pane label="注册" name="register" />
      </el-tabs>

      <el-form :model="form" :rules="rules" ref="formRef" label-position="top" class="custom-form">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" size="large" />
        </el-form-item>
        
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password size="large" />
        </el-form-item>
        
        <el-form-item v-if="mode === 'register'" label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" type="password" placeholder="请再次输入密码" show-password size="large" />
        </el-form-item>
        
        <el-form-item v-if="mode === 'register'" label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="可选填" size="large" />
        </el-form-item>

        <div class="mt-8">
          <button type="button" class="w-full gradient-btn flex justify-center items-center h-12 text-base" :disabled="loading" @click="onSubmit">
            <i v-if="loading" class="fa fa-spinner fa-spin mr-2"></i>
            {{ mode === 'login' ? '登 录' : '注 册' }}
          </button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage, FormInstance, FormRules } from 'element-plus';
import { login, register } from '../services/authApi';

const router = useRouter();
const mode = ref<'login' | 'register'>('login');

interface LoginForm {
  username: string;
  password: string;
  confirmPassword: string;
  email: string;
}

const form = reactive<LoginForm>({
  username: '',
  password: '',
  confirmPassword: '',
  email: '',
});

const rules: FormRules<LoginForm> = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  confirmPassword: [
    {
      validator: (_rule, value, callback) => {
        if (mode.value === 'register' && !value) {
          callback(new Error('请再次输入密码'));
          return;
        }
        if (mode.value === 'register' && value !== form.password) {
          callback(new Error('两次密码不一致'));
          return;
        }
        callback();
      },
      trigger: 'blur'
    }
  ],
  email: [],
};

const formRef = ref<FormInstance>();
const loading = ref(false);

const onSubmit = () => {
  if (!formRef.value) return;
  formRef.value.validate(async (valid) => {
    if (!valid) return;
    loading.value = true;
    try {
      if (mode.value === 'login') {
        const res = await login({ username: form.username, password: form.password });
        const token = res.data.token;
        localStorage.setItem('token', token);
        ElMessage.success('登录成功');
        router.push('/');
      } else {
        await register({
          username: form.username,
          password: form.password,
          email: form.email || undefined,
        });
        ElMessage.success('注册成功，请登录');
        mode.value = 'login';
      }
    } catch (error: any) {
      console.error('提交失败:', error);
      const msg = error?.response?.data?.message || (mode.value === 'login' ? '登录失败，请检查用户名或密码' : '注册失败，请稍后重试');
      ElMessage.error(msg);
    } finally {
      loading.value = false;
    }
  });
};
</script>

<style scoped>
:deep(.custom-tabs .el-tabs__item) {
  font-size: 16px;
  padding: 0 20px;
  height: 44px;
  line-height: 44px;
}
:deep(.custom-form .el-form-item__label) {
  font-weight: 500;
  color: #475569;
}
:deep(.custom-form .el-input__wrapper) {
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05), inset 0 0 0 1px #e2e8f0;
  border-radius: 0.75rem;
  padding-left: 1rem;
  padding-right: 1rem;
  background-color: #f8fafc;
  transition: all 0.3s ease;
}
:deep(.custom-form .el-input__wrapper:hover) {
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05), inset 0 0 0 1px #cbd5e1;
}
:deep(.custom-form .el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.2), inset 0 0 0 1px #3b82f6;
  background-color: #ffffff;
}
</style>
