import { shallowMount } from '@vue/test-utils'
import About from '@/views/About.vue'

describe('About.vue', () => {
  it('renders properly', () => {
    const wrapper = shallowMount(About)
    expect(wrapper.find('h1').exists())
  })
})
