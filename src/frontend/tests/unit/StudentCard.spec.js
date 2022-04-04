import { shallowMount } from '@vue/test-utils'
import Button from '@/components/Button.vue'


describe('Button.vue test', () => {
  it('renders message when component is created', () => {
    // render the component
    const wrapper = shallowMount(Button, {
      props: {
        title: 'Vue Project'
      }
    })

    // check the name of the component
    expect(wrapper.vm.$options.name).toMatch('Header')

    // check that the title is rendered
    expect(wrapper.text()).toMatch('Vue Project')
  })
})