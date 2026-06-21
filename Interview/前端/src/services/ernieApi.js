import http from './http';

export const generateQuestions = async (jdContent, resumeContent, count = 5) => {
  try {
    const response = await http.post('/ai/generate-questions', {
      jdContent,
      resumeContent,
      count,
    });

    if (!response.data || !response.data.success) {
      throw new Error(response.data?.message || '生成问题失败');
    }

    const result = response.data.data;
    if (Array.isArray(result) && result.length > 0) {
      return result;
    }
    throw new Error('返回结果不是有效的问题列表');
  } catch (error) {
    console.error('生成问题失败:', error);
    throw error;
  }
};

export const analyzeAnswer = async (question, answer) => {
  try {
    const response = await http.post('/ai/analyze-answer', {
      question,
      answer,
    });

    if (!response.data || !response.data.success) {
      throw new Error(response.data?.message || '分析回答失败');
    }

    return response.data.data;
  } catch (error) {
    console.error('分析回答失败:', error);
    throw error;
  }
};
