import { expect } from 'chai'
import { shallowMount } from '@vue/test-utils'
import TitleComponent from '@/components/TitleComponent.vue'

describe('TitleComponent.vue', () => {
  it('renders page title', () => {
    const wrapper = shallowMount(TitleComponent)
    expect(wrapper.find('h1').text()).equal('Notícias cadastradas')
  })
})
