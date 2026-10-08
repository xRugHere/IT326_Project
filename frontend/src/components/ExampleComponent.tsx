// I made this example component to show how we can set our website up to be modular


// declare type for ExampleComponent parameters/props
type ExampleComponentProps = {
  image: string
  alt?: string
}

/* 
ExampleComponent is a reusable component that that just accepts an image and optional text if it fails to load
Uses the type ExampleComponentProps to define its props
image and alt are the props that are pased, you can see them used in the img tag below
*/
function ExampleComponent({ image, alt = '' }: ExampleComponentProps) {
  return (
    <div
      style={{
        width: 200,
        height: 200,
        border: '0px solid black',
        boxShadow: '0px 4px 10px rgba(0, 0, 0, 0.3)',
        borderRadius: 20,
        overflow: 'hidden',
      }}
    >
      <img
        src={image}
        alt={alt}
        style={{ width: '100%', height: '100%', objectFit: 'contain' }}
      />
    </div>
  )
}

export default ExampleComponent
