import Vue from 'vue'
import Vuex from 'vuex'

import services from '../http'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        news: []
    },
    mutations: {
        ['SET_NEWS_LIST'] (state, payload) {
            state.news = payload
        }
    },
    actions: {
        async actionGetNewslist({ commit }) {
            const response = await services.api.getNewsList()
            commit('SET_NEWS_LIST', response.data)
        },
        async actionGetNewById(state, payload) {
            const response = await services.api.getNewById({id: payload})
            return response;
        },
        async actionCollectNew({dispatch}, payload) {
            try {
                const response = await services.api.collectNew(payload)
                await dispatch('actionGetNewslist')
                return response;
            } catch(error) {
                return error;
            }
        },
        async actionFilterNewsByTitle({commit, dispatch}, payload) {
            if (payload === '') {
                await dispatch('actionGetNewslist')
                return
            }
            try {
                const response = await services.api.getNewsByTitle({string: payload})
                commit('SET_NEWS_LIST', response.data)
                return response;
            } catch (error) {
                commit('SET_NEWS_LIST', [])
            }
        }
    },
    getters: {
        getNewsList: state => state.news
    },
    modules: {}
})
