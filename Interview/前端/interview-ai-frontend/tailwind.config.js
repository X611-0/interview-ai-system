/** @type {import('tailwindcss').Config} */
module.exports = {
  // 指定Tailwind要扫描的文件路径，确保能识别你的组件和模板中的类名
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      // 自定义颜色
      colors: {
        primary: '#165DFF',       // 主色调：蓝色，代表专业和信任
        secondary: '#FF7D00',     // 辅助色：橙色，用于强调和CTA按钮
        success: '#00B42A',       // 成功状态：绿色
        warning: '#FF7D00',       // 警告状态：橙色
        danger: '#F53F3F',        // 危险状态：红色
        neutral: {
          100: '#F5F7FA',         // 最浅的中性色，用于背景
          200: '#E5E6EB',         // 浅灰，用于分割线
          300: '#C9CDD4',         // 中灰，用于次要文本
          400: '#86909C',         // 深灰，用于辅助文本
          500: '#4E5969',         // 更深灰，用于占位文本
          600: '#272E3B',         // 接近黑色的灰，用于主要文本
          700: '#1D2129',         // 几乎是黑色，用于标题
        }
      },
      // 自定义字体
      fontFamily: {
        inter: ['Inter', 'system-ui', 'sans-serif'],
      },
      // 自定义工具类
      boxShadow: {
        'card': '0 4px 20px rgba(0, 0, 0, 0.08)',
        'card-hover': '0 8px 30px rgba(0, 0, 0, 0.12)',
      },
      spacing: {
        '128': '32rem',
      },
      animation: {
        'fade-in': 'fadeIn 0.5s ease-in-out',
        'slide-up': 'slideUp 0.4s ease-out',
      },
      keyframes: {
        fadeIn: {
          '0%': { opacity: '0' },
          '100%': { opacity: '1' },
        },
        slideUp: {
          '0%': { transform: 'translateY(20px)', opacity: '0' },
          '100%': { transform: 'translateY(0)', opacity: '1' },
        }
      }
    },
  },
  // 插件配置
  plugins: [
    // 引入Tailwind的Forms插件，用于美化表单元素
    require('@tailwindcss/forms'),
    // 引入Tailwind的Typography插件，用于排版
    require('@tailwindcss/typography'),
  ],
}