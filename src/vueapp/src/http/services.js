export default {
    api: {
        getNewsList: { method: 'get', url:'api/v1/news/list' },
        getNewById: { method: 'get', url:'api/v1/news{/id}' },
        getNewsByTitle: { method: 'get', url:'api/v1/news/title{/string}' },
        collectNew: { method: 'post', url:'api/v1/news' }
    }
}