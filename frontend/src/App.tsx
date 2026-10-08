import ExampleComponent from './components/ExampleComponent'
import big1 from './assets/images/big1.jpeg'

function App() {
  return (
    <>
      <p>Boiler plate for project w/ example component</p>
      <ExampleComponent image={big1} alt="Example image" />
    </>
  )
}

export default App