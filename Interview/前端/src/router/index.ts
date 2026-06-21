import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import InterviewView from '../views/InterviewView.vue'
import ReportView from '../views/ReportView.vue'
import LoginView from '../views/LoginView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/interview',
    name: 'interview',
    component: InterviewView
  },
  {
    path: '/report',
    name: 'report',
    component: ReportView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router